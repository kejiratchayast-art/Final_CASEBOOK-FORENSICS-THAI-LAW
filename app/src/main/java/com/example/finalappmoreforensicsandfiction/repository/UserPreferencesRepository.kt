package com.example.finalappmoreforensicsandfiction.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.finalappmoreforensicsandfiction.data.AppMode
import com.example.finalappmoreforensicsandfiction.data.FontSizeOption
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserPreferencesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("casebook_prefs", Context.MODE_PRIVATE)

    private val _userState = MutableStateFlow(loadState())
    val userState: StateFlow<UserProgressState> = _userState.asStateFlow()

    private fun loadState(): UserProgressState {
        val fontSizeStr = prefs.getString("font_size", FontSizeOption.NORMAL.name) ?: FontSizeOption.NORMAL.name
        val fontSize = try { FontSizeOption.valueOf(fontSizeStr) } catch (_: Exception) { FontSizeOption.NORMAL }

        val soundEnabled = prefs.getBoolean("sound_enabled", false)
        val showHint = prefs.getBoolean("show_hint", false)

        val activeModeStr = prefs.getString("active_mode", AppMode.READ_AND_LEARN.name) ?: AppMode.READ_AND_LEARN.name
        val activeMode = try { AppMode.valueOf(activeModeStr) } catch (_: Exception) { AppMode.READ_AND_LEARN }

        val completedCases = prefs.getStringSet("completed_cases", emptySet()) ?: emptySet()
        val activeCaseId = prefs.getString("active_case_id", null)
        val bookmarkedCases = prefs.getStringSet("bookmarked_cases", emptySet()) ?: emptySet()
        val revealedResolutions = prefs.getStringSet("revealed_resolutions", emptySet()) ?: emptySet()

        // Load case steps
        val stepMap = mutableMapOf<String, Int>()
        listOf("case_01", "case_02", "case_03").forEach { caseId ->
            val step = prefs.getInt("step_$caseId", 0)
            stepMap[caseId] = step
        }

        // Load collected evidence
        val evidenceMap = mutableMapOf<String, Set<String>>()
        listOf("case_01", "case_02", "case_03").forEach { caseId ->
            val evidenceSet = prefs.getStringSet("evidence_$caseId", emptySet()) ?: emptySet()
            evidenceMap[caseId] = evidenceSet
        }

        // Load notes
        val notesMap = mutableMapOf<String, String>()
        listOf("case_01", "case_02", "case_03").forEach { caseId ->
            val note = prefs.getString("note_$caseId", "") ?: ""
            if (note.isNotEmpty()) {
                notesMap[caseId] = note
            }
        }

        // Load saved AI feedback
        val aiFeedbackMap = mutableMapOf<String, String>()
        listOf("case_01", "case_02", "case_03").forEach { caseId ->
            val fb = prefs.getString("ai_fb_$caseId", "") ?: ""
            if (fb.isNotEmpty()) {
                aiFeedbackMap[caseId] = fb
            }
        }

        // Load activity answers
        val activityAnswersMap = mutableMapOf<String, Map<String, Int>>()
        listOf("case_01", "case_02", "case_03").forEach { caseId ->
            val caseAnswers = mutableMapOf<String, Int>()
            prefs.all.keys.filter { it.startsWith("act_ans_${caseId}_") }.forEach { key ->
                val actId = key.removePrefix("act_ans_${caseId}_")
                val ansId = prefs.getInt(key, -1)
                if (ansId != -1) {
                    caseAnswers[actId] = ansId
                }
            }
            activityAnswersMap[caseId] = caseAnswers
        }

        // Load completed activities
        val activityCompletedMap = mutableMapOf<String, Set<String>>()
        listOf("case_01", "case_02", "case_03").forEach { caseId ->
            val completedSet = prefs.getStringSet("act_completed_$caseId", emptySet()) ?: emptySet()
            activityCompletedMap[caseId] = completedSet
        }

        return UserProgressState(
            completedCaseIds = completedCases,
            activeCaseId = activeCaseId,
            activeMode = activeMode,
            fontSizeOption = fontSize,
            isSoundEnabled = soundEnabled,
            showHotspotLocationsHint = showHint,
            caseStepMap = stepMap,
            collectedEvidenceMap = evidenceMap,
            bookmarkedCaseIds = bookmarkedCases,
            caseNotesMap = notesMap,
            activityAnswersMap = activityAnswersMap,
            activityCompletedMap = activityCompletedMap,
            revealedResolutionCaseIds = revealedResolutions,
            savedAiFeedbackMap = aiFeedbackMap
        )
    }

    fun updateFontSize(option: FontSizeOption) {
        prefs.edit().putString("font_size", option.name).apply()
        _userState.value = _userState.value.copy(fontSizeOption = option)
    }

    fun updateSoundEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("sound_enabled", enabled).apply()
        _userState.value = _userState.value.copy(isSoundEnabled = enabled)
    }

    fun toggleHotspotLocationsHint() {
        val newHintState = !_userState.value.showHotspotLocationsHint
        prefs.edit().putBoolean("show_hint", newHintState).apply()
        _userState.value = _userState.value.copy(showHotspotLocationsHint = newHintState)
    }

    fun updateActiveMode(mode: AppMode) {
        prefs.edit().putString("active_mode", mode.name).apply()
        _userState.value = _userState.value.copy(activeMode = mode)
    }

    fun setActiveCaseId(caseId: String?) {
        prefs.edit().putString("active_case_id", caseId).apply()
        _userState.value = _userState.value.copy(activeCaseId = caseId)
    }

    fun updateCaseStep(caseId: String, step: Int) {
        prefs.edit().putInt("step_$caseId", step).apply()
        val updatedSteps = _userState.value.caseStepMap.toMutableMap()
        updatedSteps[caseId] = step
        _userState.value = _userState.value.copy(caseStepMap = updatedSteps)
    }

    fun collectEvidence(caseId: String, hotspotId: String) {
        val currentSet = _userState.value.collectedEvidenceMap[caseId] ?: emptySet()
        val newSet = currentSet + hotspotId
        prefs.edit().putStringSet("evidence_$caseId", newSet).apply()

        val updatedMap = _userState.value.collectedEvidenceMap.toMutableMap()
        updatedMap[caseId] = newSet
        _userState.value = _userState.value.copy(collectedEvidenceMap = updatedMap)
    }

    fun toggleBookmark(caseId: String) {
        val currentBookmarks = _userState.value.bookmarkedCaseIds
        val newBookmarks = if (currentBookmarks.contains(caseId)) {
            currentBookmarks - caseId
        } else {
            currentBookmarks + caseId
        }
        prefs.edit().putStringSet("bookmarked_cases", newBookmarks).apply()
        _userState.value = _userState.value.copy(bookmarkedCaseIds = newBookmarks)
    }

    fun saveCaseNote(caseId: String, noteText: String) {
        prefs.edit().putString("note_$caseId", noteText).apply()
        val updatedNotes = _userState.value.caseNotesMap.toMutableMap()
        updatedNotes[caseId] = noteText
        _userState.value = _userState.value.copy(caseNotesMap = updatedNotes)
    }

    fun saveAiFeedback(caseId: String, feedbackText: String) {
        prefs.edit().putString("ai_fb_$caseId", feedbackText).apply()
        val updatedMap = _userState.value.savedAiFeedbackMap.toMutableMap()
        updatedMap[caseId] = feedbackText
        _userState.value = _userState.value.copy(savedAiFeedbackMap = updatedMap)
    }

    fun saveActivityAnswer(caseId: String, activityId: String, optionId: Int, isCorrect: Boolean) {
        prefs.edit().putInt("act_ans_${caseId}_$activityId", optionId).apply()

        val updatedAnswers = _userState.value.activityAnswersMap.toMutableMap()
        val caseAnswers = (updatedAnswers[caseId] ?: emptyMap()).toMutableMap()
        caseAnswers[activityId] = optionId
        updatedAnswers[caseId] = caseAnswers

        val updatedCompleted = _userState.value.activityCompletedMap.toMutableMap()
        val caseCompletedSet = (updatedCompleted[caseId] ?: emptySet()).toMutableSet()
        if (isCorrect) {
            caseCompletedSet.add(activityId)
            prefs.edit().putStringSet("act_completed_$caseId", caseCompletedSet).apply()
            updatedCompleted[caseId] = caseCompletedSet
        }

        _userState.value = _userState.value.copy(
            activityAnswersMap = updatedAnswers,
            activityCompletedMap = updatedCompleted
        )
    }

    fun markResolutionRevealed(caseId: String) {
        val currentRevealed = _userState.value.revealedResolutionCaseIds
        val newRevealed = currentRevealed + caseId
        prefs.edit().putStringSet("revealed_resolutions", newRevealed).apply()
        _userState.value = _userState.value.copy(revealedResolutionCaseIds = newRevealed)
    }

    fun completeCase(caseId: String) {
        val newCompleted = _userState.value.completedCaseIds + caseId
        prefs.edit().putStringSet("completed_cases", newCompleted).apply()
        _userState.value = _userState.value.copy(completedCaseIds = newCompleted)
    }

    fun resetPersonalNotes() {
        listOf("case_01", "case_02", "case_03").forEach { caseId ->
            prefs.edit().remove("note_$caseId").apply()
            prefs.edit().remove("ai_fb_$caseId").apply()
        }
        _userState.value = _userState.value.copy(
            caseNotesMap = emptyMap(),
            savedAiFeedbackMap = emptyMap()
        )
    }

    fun resetAllProgress() {
        prefs.edit().clear().apply()
        _userState.value = loadState()
    }
}
