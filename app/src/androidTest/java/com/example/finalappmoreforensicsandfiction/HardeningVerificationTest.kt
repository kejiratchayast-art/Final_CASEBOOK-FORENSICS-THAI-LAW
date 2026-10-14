package com.example.finalappmoreforensicsandfiction

import android.app.Application
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.finalappmoreforensicsandfiction.data.*
import com.example.finalappmoreforensicsandfiction.repository.UserPreferencesRepository
import com.example.finalappmoreforensicsandfiction.ui.components.MainTab
import com.example.finalappmoreforensicsandfiction.viewmodel.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.json.JSONObject
import org.json.JSONArray

/** Run seed and verifyAfterRestart in separate instrumentation processes. */
@RunWith(AndroidJUnit4::class)
class HardeningVerificationTest {
    private val instrumentation get() = InstrumentationRegistry.getInstrumentation()
    private val context get() = instrumentation.targetContext
    private val backup get() = context.getSharedPreferences("hardening_backup", Context.MODE_PRIVATE)
    private val case get() = CasesProvider.case1
    private val expected = AiFeedbackResult("case_01", "Test hypothesis", listOf("Observed trace"),
        listOf("Intent is not established"), listOf("Timing remains unknown"), "What is still missing?", false, "Verification record")

    @Test fun seed() {
        check(!backup.contains("original")) { "Restore previous verification data before seeding again" }
        val prefs = context.getSharedPreferences("casebook_prefs", Context.MODE_PRIVATE)
        val record = JSONObject().apply {
            put("note", prefs.getString("note_${case.id}", null) ?: JSONObject.NULL)
            put("feedback", prefs.getString("ai_fb_${case.id}", null) ?: JSONObject.NULL)
            put("step", if (prefs.contains("step_${case.id}")) prefs.getInt("step_${case.id}", 0) else JSONObject.NULL)
            put("evidence", if (prefs.contains("evidence_${case.id}")) JSONArray(prefs.getStringSet("evidence_${case.id}", emptySet())!!.toList()) else JSONObject.NULL)
            put("active", prefs.getString("active_case_id", null) ?: JSONObject.NULL)
        }
        assertTrue(backup.edit().putString("original", record.toString()).commit())
        val repository = UserPreferencesRepository(context)
        repository.saveCaseNote(case.id, "Restart verification note")
        repository.saveAiFeedback(case.id, AiFeedbackStorage.encode(expected))
        repository.updateCaseStep(case.id, 3)
        repository.collectEvidence(case.id, case.hotspots.first().id)
        assertTrue(prefs.edit().putBoolean("verification_flush", true).commit())
    }

    @Test fun verifyAfterRestart() {
        val original = JSONObject(backup.getString("original", null) ?: error("Run seed first"))
        val prefs = context.getSharedPreferences("casebook_prefs", Context.MODE_PRIVATE)
        try {
            val state = UserPreferencesRepository(context).userState.value
            assertEquals("Restart verification note", state.caseNotesMap[case.id])
            assertTrue(state.collectedEvidenceMap[case.id]!!.contains(case.hotspots.first().id))
            assertEquals(expected, AiFeedbackStorage.decode(state.savedAiFeedbackMap[case.id]))
            assertNull(AiFeedbackStorage.decode("Old plain-text feedback"))
            instrumentation.runOnMainSync {
                val vm = MainViewModel(context.applicationContext as Application)
                vm.openCase(case)
                assertEquals(MainTab.CASES, vm.currentTab.value)
                assertEquals(ScreenState.INVESTIGATION_NOTEBOOK, vm.currentScreen.value)
                assertEquals(expected, vm.aiResult.value)
                vm.openCase(CasesProvider.case2)
                assertTrue(vm.aiResult.value == null || vm.aiResult.value!!.caseId == CasesProvider.case2.id)
                vm.openCase(case)
                assertEquals(expected, vm.aiResult.value)
            }
        } finally {
            val edit = prefs.edit().remove("verification_flush")
            for ((field, key) in listOf("note" to "note_${case.id}", "feedback" to "ai_fb_${case.id}", "active" to "active_case_id")) {
                if (original.isNull(field)) edit.remove(key) else edit.putString(key, original.getString(field))
            }
            if (original.isNull("step")) edit.remove("step_${case.id}") else edit.putInt("step_${case.id}", original.getInt("step"))
            if (original.isNull("evidence")) edit.remove("evidence_${case.id}") else {
                val array = original.getJSONArray("evidence")
                edit.putStringSet("evidence_${case.id}", (0 until array.length()).map { array.getString(it) }.toSet())
            }
            check(edit.commit())
            backup.edit().clear().commit()
        }
    }

    @Test fun offlineDoesNotOverwriteSavedFeedback() {
        lateinit var vm: MainViewModel
        instrumentation.runOnMainSync {
            vm = MainViewModel(context.applicationContext as Application)
            vm.openCase(case)
            vm.requestAiDeductionReview(case, "Offline draft must remain available")
        }
        val deadline = System.currentTimeMillis() + 12000
        while (vm.isAiLoading.value && System.currentTimeMillis() < deadline) Thread.sleep(50)
        assertFalse("Request must finish", vm.isAiLoading.value)
        assertTrue("Requires stopped backend", vm.aiResult.value!!.isDemoResponse)
        assertEquals("Offline draft must remain available", vm.aiResult.value!!.userHypothesis)
        assertEquals(expected, AiFeedbackStorage.decode(UserPreferencesRepository(context).userState.value.savedAiFeedbackMap[case.id]))
    }
}
