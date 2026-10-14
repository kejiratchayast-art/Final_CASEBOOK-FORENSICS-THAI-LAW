package com.example.finalappmoreforensicsandfiction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.finalappmoreforensicsandfiction.data.CasesProvider
import com.example.finalappmoreforensicsandfiction.data.FontSizeOption
import com.example.finalappmoreforensicsandfiction.service.ChatStore
import com.example.finalappmoreforensicsandfiction.ui.components.BottomNavBar
import com.example.finalappmoreforensicsandfiction.ui.components.MainTab
import com.example.finalappmoreforensicsandfiction.ui.screens.ActivityScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.AnatomyScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.BodyExaminationScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.CaseLibraryScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.CaseOverviewScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.CaseResolutionScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.ChapterReaderScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.HomeScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.InvestigationSceneScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.KnowledgeLibraryScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.LawPanelScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.NotebookScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.PreCaseBriefingScreen
import com.example.finalappmoreforensicsandfiction.ui.screens.SettingsScreen
import com.example.finalappmoreforensicsandfiction.ui.theme.CasebookTheme
import com.example.finalappmoreforensicsandfiction.viewmodel.MainViewModel
import com.example.finalappmoreforensicsandfiction.viewmodel.ScreenState

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CasebookTheme {
                CasebookApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CasebookApp(viewModel: MainViewModel) {
    val userState by viewModel.userState.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedCase by viewModel.selectedCase.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    val aiResult by viewModel.aiResult.collectAsState()
    val notebookInitialTab by viewModel.notebookInitialTab.collectAsState()

    val activeCaseData = selectedCase ?: CasesProvider.case1

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selectedTab = currentTab,
                onTabSelected = { tab ->
                    viewModel.selectTab(tab)
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                ScreenState.HOME -> {
                    HomeScreen(
                        userState = userState,
                        onCaseSelect = { caseData ->
                            viewModel.openCase(caseData)
                        },
                        onOpenPreCaseBriefing = {
                            viewModel.navigateToScreen(ScreenState.PRE_CASE_BRIEFING)
                        },
                        onOpenLibrary = {
                            viewModel.navigateToScreen(ScreenState.KNOWLEDGE_LIBRARY)
                        },
                        onOpenSection = { tab ->
                            viewModel.selectTab(tab)
                        },
                        onModeChanged = { mode ->
                            viewModel.setAppMode(mode)
                        }
                    )
                }

                ScreenState.PRE_CASE_BRIEFING -> {
                    PreCaseBriefingScreen(
                        onBackClick = {
                            viewModel.navigateToScreen(ScreenState.HOME)
                        },
                        onProceedToInvestigation = {
                            viewModel.navigateToScreen(ScreenState.CASE_OVERVIEW)
                        }
                    )
                }

                ScreenState.CASE_LIBRARY -> {
                    CaseLibraryScreen(
                        userState = userState,
                        onCaseSelect = { caseData ->
                            viewModel.openCase(caseData)
                        }
                    )
                }

                ScreenState.CASE_OVERVIEW -> {
                    CaseOverviewScreen(
                        caseData = activeCaseData,
                        userState = userState,
                        onBackClick = {
                            if (currentTab == MainTab.CASES) {
                                viewModel.navigateToScreen(ScreenState.CASE_LIBRARY)
                            } else {
                                viewModel.navigateToScreen(ScreenState.HOME)
                            }
                        },
                        onStartCase = {
                            viewModel.updateCaseStep(activeCaseData.id, 1)
                            viewModel.navigateToScreen(ScreenState.CHAPTER_READER)
                        },
                        onOpenAskAi = {
                            viewModel.openNotebookWithTab(4)
                        },
                        onOpenBodyExamination = {
                            viewModel.openBodyExamination()
                        },
                        onModeChanged = { mode ->
                            viewModel.setAppMode(mode)
                        },
                        onBookmarkToggle = {
                            viewModel.toggleBookmark(activeCaseData.id)
                        }
                    )
                }

                ScreenState.CHAPTER_READER -> {
                    ChapterReaderScreen(
                        caseData = activeCaseData,
                        userState = userState,
                        onBackClick = {
                            viewModel.navigateToScreen(ScreenState.CASE_OVERVIEW)
                        },
                        onNavigateToScene = {
                            viewModel.updateCaseStep(activeCaseData.id, 2)
                            viewModel.navigateToScreen(ScreenState.INVESTIGATION_SCENE)
                        },
                        onNavigateToActivities = {
                            viewModel.updateCaseStep(activeCaseData.id, 4)
                            viewModel.navigateToScreen(ScreenState.LEARNING_ACTIVITIES)
                        },
                        onFontSizeCycle = {
                            val nextOption = when (userState.fontSizeOption) {
                                FontSizeOption.SMALL -> FontSizeOption.NORMAL
                                FontSizeOption.NORMAL -> FontSizeOption.LARGE
                                FontSizeOption.LARGE -> FontSizeOption.SMALL
                            }
                            viewModel.setFontSize(nextOption)
                        },
                        onBookmarkToggle = {
                            viewModel.toggleBookmark(activeCaseData.id)
                        }
                    )
                }

                ScreenState.INVESTIGATION_SCENE -> {
                    InvestigationSceneScreen(
                        caseData = activeCaseData,
                        userState = userState,
                        onBackClick = {
                            viewModel.navigateToScreen(ScreenState.CHAPTER_READER)
                        },
                        onCollectEvidence = { hotspotId ->
                            viewModel.collectEvidence(activeCaseData.id, hotspotId)
                        },
                        onOpenNotebook = {
                            viewModel.updateCaseStep(activeCaseData.id, 3)
                            viewModel.openNotebookWithTab(3)
                        },
                        onOpenAskAi = {
                            viewModel.openNotebookWithTab(4)
                        },
                        onOpenBodyExamination = {
                            viewModel.openBodyExamination()
                        },
                        onToggleHint = {
                            viewModel.toggleHotspotLocationsHint()
                        },
                        onNavigateToActivities = {
                            viewModel.updateCaseStep(activeCaseData.id, 4)
                            viewModel.navigateToScreen(ScreenState.LEARNING_ACTIVITIES)
                        },
                        onBookmarkToggle = {
                            viewModel.toggleBookmark(activeCaseData.id)
                        }
                    )
                }

                ScreenState.INVESTIGATION_NOTEBOOK -> {
                    NotebookScreen(
                        caseData = activeCaseData,
                        userState = userState,
                        initialTab = notebookInitialTab,
                        isAiLoading = isAiLoading,
                        aiResult = aiResult,
                        onBackClick = {
                            viewModel.navigateToScreen(ScreenState.INVESTIGATION_SCENE)
                        },
                        onSaveNote = { noteText ->
                            viewModel.saveCaseNote(activeCaseData.id, noteText)
                        },
                        onRequestAiReview = { hypothesis ->
                            viewModel.requestAiDeductionReview(activeCaseData, hypothesis)
                        },
                        onBookmarkToggle = {
                            viewModel.toggleBookmark(activeCaseData.id)
                        }
                    )
                }

                ScreenState.LEARNING_ACTIVITIES -> {
                    ActivityScreen(
                        caseData = activeCaseData,
                        userState = userState,
                        onBackClick = {
                            viewModel.navigateToScreen(ScreenState.INVESTIGATION_SCENE)
                        },
                        onSubmitAnswer = { activityId, optionId, isCorrect ->
                            viewModel.submitActivityAnswer(activeCaseData.id, activityId, optionId, isCorrect)
                        },
                        onNavigateToLaw = {
                            viewModel.updateCaseStep(activeCaseData.id, 5)
                            viewModel.navigateToScreen(ScreenState.THAI_LAW_PANEL)
                        },
                        onBookmarkToggle = {
                            viewModel.toggleBookmark(activeCaseData.id)
                        }
                    )
                }

                ScreenState.THAI_LAW_PANEL -> {
                    LawPanelScreen(
                        caseData = activeCaseData,
                        userState = userState,
                        onBackClick = {
                            viewModel.navigateToScreen(ScreenState.LEARNING_ACTIVITIES)
                        },
                        onNavigateToResolution = {
                            viewModel.updateCaseStep(activeCaseData.id, 6)
                            viewModel.navigateToScreen(ScreenState.CASE_RESOLUTION)
                        },
                        onBookmarkToggle = {
                            viewModel.toggleBookmark(activeCaseData.id)
                        }
                    )
                }

                ScreenState.CASE_RESOLUTION -> {
                    CaseResolutionScreen(
                        caseData = activeCaseData,
                        userState = userState,
                        onBackClick = {
                            viewModel.navigateToScreen(ScreenState.THAI_LAW_PANEL)
                        },
                        onRevealResolution = {
                            viewModel.markResolutionRevealed(activeCaseData.id)
                        },
                        onCompleteAndReturnHome = {
                            viewModel.completeCase(activeCaseData.id)
                            viewModel.updateCaseStep(activeCaseData.id, 7)
                            viewModel.selectTab(MainTab.HOME)
                        },
                        onReplayCase = {
                            viewModel.updateCaseStep(activeCaseData.id, 1)
                            viewModel.navigateToScreen(ScreenState.CHAPTER_READER)
                        },
                        onBookmarkToggle = {
                            viewModel.toggleBookmark(activeCaseData.id)
                        }
                    )
                }

                ScreenState.AUTOPSY_VIEW -> {
                    BodyExaminationScreen(
                        caseData = activeCaseData,
                        onBackClick = {
                            viewModel.navigateToScreen(ScreenState.CASE_OVERVIEW)
                        },
                        onOpenAskAiWithPrompt = { prompt ->
                            val chatStore = ChatStore(viewModel.getApplication(), activeCaseData.id)
                            chatStore.saveDraft(prompt)
                            viewModel.openNotebookWithTab(4)
                        }
                    )
                }

                ScreenState.ANATOMY_VIEW -> {
                    AnatomyScreen(
                        onOpenCase = { caseId ->
                            val caseData = CasesProvider.getCaseById(caseId) ?: CasesProvider.case1
                            viewModel.openCase(caseData)
                        }
                    )
                }

                ScreenState.KNOWLEDGE_LIBRARY -> {
                    KnowledgeLibraryScreen(
                        onCaseSelect = { caseId ->
                            val caseData = CasesProvider.getCaseById(caseId) ?: CasesProvider.case1
                            viewModel.openCase(caseData)
                        },
                        onOpenPreCaseBriefing = {
                            viewModel.navigateToScreen(ScreenState.PRE_CASE_BRIEFING)
                        }
                    )
                }

                ScreenState.SETTINGS -> {
                    SettingsScreen(
                        userState = userState,
                        onFontSizeSelect = { option ->
                            viewModel.setFontSize(option)
                        },
                        onSoundToggle = { enabled ->
                            viewModel.setSoundEnabled(enabled)
                        },
                        onModeChanged = { mode ->
                            viewModel.setAppMode(mode)
                        },
                        onResetNotes = {
                            viewModel.resetPersonalNotes()
                        },
                        onResetProgress = {
                            viewModel.resetAllProgress()
                        }
                    )
                }
            }
        }
    }
}
