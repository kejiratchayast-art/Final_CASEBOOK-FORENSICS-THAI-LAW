package com.example.finalappmoreforensicsandfiction.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalappmoreforensicsandfiction.data.AiFeedbackResult
import com.example.finalappmoreforensicsandfiction.data.AppMode
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.FontSizeOption
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.repository.UserPreferencesRepository
import com.example.finalappmoreforensicsandfiction.service.DeductionCoachService
import com.example.finalappmoreforensicsandfiction.ui.components.MainTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.finalappmoreforensicsandfiction.data.AiFeedbackStorage

enum class ScreenState {
    HOME,
    PRE_CASE_BRIEFING,
    CASE_LIBRARY,
    CASE_OVERVIEW,
    CHAPTER_READER,
    INVESTIGATION_SCENE,
    INVESTIGATION_NOTEBOOK,
    LEARNING_ACTIVITIES,
    THAI_LAW_PANEL,
    CASE_RESOLUTION,
    KNOWLEDGE_LIBRARY,
    AUTOPSY_VIEW,
    ANATOMY_VIEW,
    SETTINGS
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserPreferencesRepository(application)
    val userState: StateFlow<UserProgressState> = repository.userState

    private val _currentTab = MutableStateFlow(MainTab.HOME)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    private val _currentScreen = MutableStateFlow(ScreenState.HOME)
    val currentScreen: StateFlow<ScreenState> = _currentScreen.asStateFlow()

    private val _selectedCase = MutableStateFlow<CaseData?>(null)
    val selectedCase: StateFlow<CaseData?> = _selectedCase.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    private val _aiResult = MutableStateFlow<AiFeedbackResult?>(null)
    val aiResult: StateFlow<AiFeedbackResult?> = _aiResult.asStateFlow()

    private var aiJob: Job? = null
    private var aiGeneration = 0
    private fun selectCase(caseData: CaseData) {
        if (_selectedCase.value?.id != caseData.id) {
            aiGeneration++; aiJob?.cancel(); _isAiLoading.value = false
            _aiResult.value = AiFeedbackStorage.decode(userState.value.savedAiFeedbackMap[caseData.id])
        }
        _selectedCase.value = caseData
        _currentTab.value = MainTab.CASES
        repository.setActiveCaseId(caseData.id)
    }
    private val _notebookInitialTab = MutableStateFlow(3)
    val notebookInitialTab: StateFlow<Int> = _notebookInitialTab.asStateFlow()

    fun selectTab(tab: MainTab) {
        _currentTab.value = tab
        when (tab) {
            MainTab.HOME -> _currentScreen.value = ScreenState.HOME
            MainTab.CASES -> _currentScreen.value = ScreenState.CASE_LIBRARY
            MainTab.ASK_AI -> {
                if (_selectedCase.value == null) {
                    _selectedCase.value = com.example.finalappmoreforensicsandfiction.data.CasesProvider.case1
                }
                _notebookInitialTab.value = 4
                _currentScreen.value = ScreenState.INVESTIGATION_NOTEBOOK
            }
            MainTab.LIBRARY -> _currentScreen.value = ScreenState.KNOWLEDGE_LIBRARY
            MainTab.SETTINGS -> _currentScreen.value = ScreenState.SETTINGS
        }
    }

    fun openCase(caseData: CaseData) {
        openCaseAndResume(caseData)
    }

    fun openCaseOverview(caseData: CaseData) {
        selectCase(caseData)
        _currentScreen.value = ScreenState.CASE_OVERVIEW
    }

    fun openCaseAndResume(caseData: CaseData) {
        selectCase(caseData)
        val step = userState.value.caseStepMap[caseData.id] ?: 0
        val clampedStep = if (step < 0) 0 else if (step > 7) 7 else step
        when (clampedStep) {
            1 -> _currentScreen.value = ScreenState.CHAPTER_READER
            2 -> _currentScreen.value = ScreenState.INVESTIGATION_SCENE
            3 -> _currentScreen.value = ScreenState.INVESTIGATION_NOTEBOOK
            4 -> _currentScreen.value = ScreenState.LEARNING_ACTIVITIES
            5 -> _currentScreen.value = ScreenState.THAI_LAW_PANEL
            6, 7 -> _currentScreen.value = ScreenState.CASE_RESOLUTION
            else -> _currentScreen.value = ScreenState.CASE_OVERVIEW
        }
    }

    fun openNotebookWithTab(tabIndex: Int) {
        _notebookInitialTab.value = tabIndex
        _currentScreen.value = ScreenState.INVESTIGATION_NOTEBOOK
    }

    fun openBodyExamination() {
        _currentScreen.value = ScreenState.AUTOPSY_VIEW
    }

    fun navigateToScreen(screen: ScreenState) {
        _currentScreen.value = screen
    }

    fun setAppMode(mode: AppMode) {
        repository.updateActiveMode(mode)
    }

    fun setFontSize(option: FontSizeOption) {
        repository.updateFontSize(option)
    }

    fun setSoundEnabled(enabled: Boolean) {
        repository.updateSoundEnabled(enabled)
    }

    fun toggleHotspotLocationsHint() {
        repository.toggleHotspotLocationsHint()
    }

    fun updateCaseStep(caseId: String, step: Int) {
        val clamped = if (step < 0) 0 else if (step > 7) 7 else step
        repository.updateCaseStep(caseId, clamped)
    }

    fun collectEvidence(caseId: String, hotspotId: String) {
        repository.collectEvidence(caseId, hotspotId)
    }

    fun toggleBookmark(caseId: String) {
        repository.toggleBookmark(caseId)
    }

    fun saveCaseNote(caseId: String, noteText: String) {
        repository.saveCaseNote(caseId, noteText)
    }

    fun requestAiDeductionReview(caseData: CaseData, userHypothesis: String) {
        if (aiJob?.isActive == true || userHypothesis.isBlank()) return
        val generation = ++aiGeneration
        _isAiLoading.value = true
        aiJob = viewModelScope.launch {
            try {
                val ids = repository.userState.value.collectedEvidenceMap[caseData.id] ?: emptySet()
                val result = DeductionCoachService.reviewUserHypothesis(caseData, userHypothesis, ids)
                if (generation == aiGeneration && _selectedCase.value?.id == caseData.id) _aiResult.value = result
                if (!result.isDemoResponse) repository.saveAiFeedback(caseData.id, AiFeedbackStorage.encode(result))
            } finally { if (generation == aiGeneration) _isAiLoading.value = false }
        }
    }

    fun submitActivityAnswer(caseId: String, activityId: String, optionId: Int, isCorrect: Boolean) {
        repository.saveActivityAnswer(caseId, activityId, optionId, isCorrect)
    }

    fun markResolutionRevealed(caseId: String) {
        repository.markResolutionRevealed(caseId)
    }

    fun completeCase(caseId: String) {
        repository.completeCase(caseId)
    }

    fun resetPersonalNotes() {
        aiGeneration++; aiJob?.cancel(); _isAiLoading.value = false
        repository.resetPersonalNotes()
        _aiResult.value = null
    }

    fun resetAllProgress() {
        aiGeneration++; aiJob?.cancel(); _isAiLoading.value = false
        repository.resetAllProgress()
        _selectedCase.value = null
        _aiResult.value = null
        _currentTab.value = MainTab.HOME
        _currentScreen.value = ScreenState.HOME
    }
}
