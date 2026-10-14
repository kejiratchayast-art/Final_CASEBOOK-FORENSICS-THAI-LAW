const fs=require('fs');const base='app/src/main/java/com/example/finalappmoreforensicsandfiction/';
let v=fs.readFileSync(base+'viewmodel/MainViewModel.kt','utf8');
v=v.replace('import kotlinx.coroutines.launch','import kotlinx.coroutines.Job\nimport kotlinx.coroutines.launch\nimport com.example.finalappmoreforensicsandfiction.data.AiFeedbackStorage');
v=v.replace('    private val _notebookInitialTab',`    private var aiJob: Job? = null
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
    private val _notebookInitialTab`);
v=v.replaceAll('        _selectedCase.value = caseData\n        repository.setActiveCaseId(caseData.id)','        selectCase(caseData)');
const a=v.indexOf('    fun requestAiDeductionReview');const b=v.indexOf('    fun submitActivityAnswer',a);
v=v.slice(0,a)+`    fun requestAiDeductionReview(caseData: CaseData, userHypothesis: String) {
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

`+v.slice(b);
v=v.replace('    fun resetPersonalNotes() {','    fun resetPersonalNotes() {\n        aiGeneration++; aiJob?.cancel(); _isAiLoading.value = false');
v=v.replace('    fun resetAllProgress() {','    fun resetAllProgress() {\n        aiGeneration++; aiJob?.cancel(); _isAiLoading.value = false');
fs.writeFileSync(base+'viewmodel/MainViewModel.kt',v);
let n=fs.readFileSync(base+'ui/screens/NotebookScreen.kt','utf8');const c=n.indexOf('                    val activeAiFb =');const d=n.indexOf('                    activeAiFb?.let',c);
n=n.slice(0,c)+`                    val activeAiFb = aiResult?.takeIf { it.caseId == caseData.id }
                        ?: com.example.finalappmoreforensicsandfiction.data.AiFeedbackStorage.decode(savedAiFbText)?.takeIf { it.caseId == caseData.id }
                    if (activeAiFb == null && !savedAiFbText.isNullOrBlank()) {
                        Text("Previously saved feedback", color = ParchmentSurface)
                        Text(savedAiFbText, color = ParchmentSurface)
                    }

`+n.slice(d);fs.writeFileSync(base+'ui/screens/NotebookScreen.kt',n);
let settings=fs.readFileSync(base+'ui/screens/SettingsScreen.kt','utf8');const x=settings.indexOf('// Font Size Setting Card');settings=settings.slice(0,x).replaceAll('tint = DeepNavy','tint = ParchmentSurface').replaceAll('color = DeepNavy','color = ParchmentSurface')+settings.slice(x);fs.writeFileSync(base+'ui/screens/SettingsScreen.kt',settings);
