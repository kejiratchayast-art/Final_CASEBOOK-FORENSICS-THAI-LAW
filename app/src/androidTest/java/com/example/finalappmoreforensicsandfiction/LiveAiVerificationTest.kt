package com.example.finalappmoreforensicsandfiction

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.finalappmoreforensicsandfiction.data.CasesProvider
import com.example.finalappmoreforensicsandfiction.repository.UserPreferencesRepository
import com.example.finalappmoreforensicsandfiction.service.DeductionCoachService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/** Requires the local Gemini backend running; a fallback is a test failure. */
@RunWith(AndroidJUnit4::class)
class LiveAiVerificationTest {
    @Test fun genuineAiFromAndroidAndSavedFeedback() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val case = CasesProvider.case1
        val evidence = case.hotspots.first()
        val result = DeductionCoachService.reviewUserHypothesis(
            case, "This observation alone cannot establish intent or identify a perpetrator.", setOf(evidence.id)
        )
        assertFalse("Live AI required: ${result.assumptionsToReconsider}", result.isDemoResponse)
        assertTrue(result.supportingEvidence.isNotEmpty())
        assertTrue(result.assumptionsToReconsider.isNotEmpty())
        assertTrue(result.remainingUncertainties.isNotEmpty())
        assertTrue(result.guidingQuestion.isNotBlank())
        val repository = UserPreferencesRepository(context)
        val oldNote = repository.userState.value.caseNotesMap[case.id].orEmpty()
        val oldFeedback = repository.userState.value.savedAiFeedbackMap[case.id].orEmpty()
        try {
            repository.saveCaseNote(case.id, "Persistence verification note")
            repository.saveAiFeedback(case.id, result.guidingQuestion)
            val reloaded = UserPreferencesRepository(context).userState.value
            assertEquals("Persistence verification note", reloaded.caseNotesMap[case.id])
            assertEquals(result.guidingQuestion, reloaded.savedAiFeedbackMap[case.id])
        } finally {
            repository.saveCaseNote(case.id, oldNote)
            repository.saveAiFeedback(case.id, oldFeedback)
        }
    }
}
