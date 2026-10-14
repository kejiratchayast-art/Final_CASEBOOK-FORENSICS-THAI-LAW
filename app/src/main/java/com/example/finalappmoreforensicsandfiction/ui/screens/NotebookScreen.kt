package com.example.finalappmoreforensicsandfiction.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import kotlinx.coroutines.CancellationException
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.AiFeedbackResult
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.CasesProvider
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.service.CasebookChat
import com.example.finalappmoreforensicsandfiction.service.ChatMessage
import com.example.finalappmoreforensicsandfiction.service.ChatStore
import com.example.finalappmoreforensicsandfiction.ui.components.EvidenceCard
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary
import com.example.finalappmoreforensicsandfiction.ui.theme.WarmParchment
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotebookScreen(
    caseData: CaseData,
    userState: UserProgressState,
    initialTab: Int = 3,
    isAiLoading: Boolean,
    aiResult: AiFeedbackResult?,
    onBackClick: () -> Unit,
    onSaveNote: (String) -> Unit,
    onRequestAiReview: (String) -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabState by remember { mutableIntStateOf(initialTab.coerceIn(0, 4)) }
    val scrollState = rememberScrollState()
    val collectedIds = userState.collectedEvidenceMap[caseData.id] ?: emptySet()
    val isBookmarked = userState.bookmarkedCaseIds.contains(caseData.id)

    val existingNote = userState.caseNotesMap[caseData.id] ?: ""
    var noteTextState by androidx.compose.runtime.saveable.rememberSaveable(caseData.id, existingNote) { mutableStateOf(existingNote) }

    val savedAiFbText = userState.savedAiFeedbackMap[caseData.id]

    // Ask AI Chat State per case
    val context = LocalContext.current
    val chatStore = remember(caseData.id) { ChatStore(context, caseData.id) }
    var chatMessages by remember(caseData.id) { mutableStateOf(chatStore.load()) }
    var chatInputState by remember(caseData.id) { mutableStateOf(chatStore.draft()) }
    var selectedChatModeState by remember { mutableStateOf("Case Evidence") }
    var isChatLoading by remember { mutableStateOf(false) }
    var chatErrorMessage by remember { mutableStateOf<String?>(null) }
    var showClearChatDialog by remember { mutableStateOf(false) }
    var selectedSourceRefModalId by remember { mutableStateOf<String?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val coroutineScope = rememberCoroutineScope()


    fun sendChatQuestion() {
        if (isChatLoading || chatInputState.isBlank()) return
        val question = chatInputState.trim()
        val mode = selectedChatModeState
        val previous = chatMessages
        isChatLoading = true
        chatErrorMessage = null
        chatStore.saveDraft(question)
        coroutineScope.launch {
            try {
                val reply = CasebookChat.ask(caseData.id, mode, question, previous, collectedIds)
                val completed = (previous + ChatMessage("user", question, mode) + reply).takeLast(60)
                chatMessages = completed
                chatStore.save(completed)
                chatInputState = ""
                chatStore.saveDraft("")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                chatInputState = question
                chatStore.saveDraft(question)
                chatErrorMessage = e.localizedMessage?.takeIf { it.isNotBlank() }
                    ?: "AI unavailable. Your draft is saved. Please retry."
            } finally {
                isChatLoading = false
            }
        }
    }

    val suggestedQuestions = when (selectedChatModeState) {
        "Thai Law" -> listOf(
            "What legal elements must be established?",
            "What information is missing from this legal analysis?",
            "What does this section mean?",
            "What is the statutory penalty, if verified?",
            "How does this law relate to the fictional case?"
        )
        "Forensics" -> listOf(
            "What are the limitations of this forensic finding?",
            "How does Locard's Exchange Principle apply here?",
            "What is the difference between class and individual characteristics?"
        )
        else -> listOf(
            "What does this evidence tell us?",
            "What should I examine next?",
            "Are there any contradictions in witness statements?"
        )
    }

    Column(modifier = modifier.fillMaxSize().imePadding().background(MaterialTheme.colorScheme.background)) {
        HeaderBar(
            title = "Dossier Notebook: ${caseData.title}",
            onBackClick = onBackClick,
            isBookmarked = isBookmarked,
            onBookmarkToggle = onBookmarkToggle
        )

        // Scrollable Tabs for 5 Tabs (Evidence, Witnesses, Timeline, Notes & AI, Ask AI)
        ScrollableTabRow(
            selectedTabIndex = selectedTabState,
            containerColor = InkBlack,
            contentColor = RestrainedGold,
            edgePadding = 12.dp
        ) {
            val tabs = listOf(
                Pair("Evidence (${collectedIds.size})", Icons.Default.Bookmark),
                Pair("Witnesses", Icons.Default.Group),
                Pair("Timeline", Icons.Default.Schedule),
                Pair("Notes & AI", Icons.Default.Edit),
                Pair("Ask AI", Icons.Default.AutoAwesome)
            )

            tabs.forEachIndexed { index, (label, icon) ->
                Tab(
                    selected = selectedTabState == index,
                    onClick = { selectedTabState = index },
                    text = {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = if (selectedTabState == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabState == index) ParchmentSurface else RestrainedGold.copy(alpha = 0.7f)
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = if (selectedTabState == index) ParchmentSurface else RestrainedGold.copy(alpha = 0.7f)
                        )
                    }
                )
            }
        }

        if (selectedTabState == 4) {
            androidx.compose.runtime.key(caseData.id) {
                CaseChatPanel(caseData.id, collectedIds, { selectedSourceRefModalId = it }, Modifier.weight(1f).fillMaxWidth())
            }
        } else Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(if (selectedTabState == 4) rememberScrollState() else scrollState)
                .padding(16.dp)
        ) {
            when (selectedTabState) {
                0 -> { // Collected Evidence
                    Text(
                        text = "Collected Physical Evidence (${collectedIds.size}/${caseData.hotspots.size})",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = InkBlack
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val collectedList = caseData.hotspots.filter { collectedIds.contains(it.id) }
                    if (collectedList.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.height(36.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No evidence saved in notebook yet.\nReturn to 2D scene exploration to tap and collect evidence.",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                    color = TextSecondary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        collectedList.forEach { evidence ->
                            EvidenceCard(
                                evidence = evidence,
                                isCollected = true,
                                onCollectClick = {}
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

                1 -> { // Witness Statements & Authored Interview Q&A
                    Text(
                        text = "Witness Interviews & Statements Log",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = InkBlack
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    caseData.witnessStatements.forEach { witness ->
                        var selectedQuestionIdState by remember { mutableStateOf<String?>(null) }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${witness.witnessName} (${witness.role})",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        ),
                                        color = InkBlack
                                    )
                                    Text(
                                        text = witness.timeRecorded,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                        color = TextSecondary
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "\"${witness.statement}\"",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp
                                    ),
                                    color = TextPrimary
                                )

                                if (witness.interviewQuestions.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Conduct Interview (Select Question):",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        ),
                                        color = AntiqueBrass
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    witness.interviewQuestions.forEach { q ->
                                        val isExpanded = selectedQuestionIdState == q.id

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (isExpanded) InkBlack else InkBlack.copy(alpha = 0.05f))
                                                .border(1.dp, CardBorderBrass, RoundedCornerShape(8.dp))
                                                .clickable {
                                                    selectedQuestionIdState = if (isExpanded) null else q.id
                                                }
                                                .padding(10.dp)
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.QuestionAnswer,
                                                    contentDescription = null,
                                                    tint = if (isExpanded) ParchmentSurface else InkBlack,
                                                    modifier = Modifier.padding(end = 6.dp)
                                                )
                                                Text(
                                                    text = q.questionText,
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 12.sp
                                                    ),
                                                    color = if (isExpanded) ParchmentSurface else InkBlack
                                                )
                                            }

                                            AnimatedVisibility(visible = isExpanded) {
                                                Column(modifier = Modifier.padding(top = 8.dp)) {
                                                    Text(
                                                        text = "Answer: \"${q.answerText}\"",
                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                            fontSize = 12.sp,
                                                            lineHeight = 17.sp
                                                        ),
                                                        color = RestrainedGold
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                if (witness.inconsistencies.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(MutedBurgundy.copy(alpha = 0.08f))
                                            .border(1.dp, MutedBurgundy.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                            .padding(10.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Warning,
                                                contentDescription = null,
                                                tint = MutedBurgundy
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "Independent Evidence Comparison:",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp
                                                ),
                                                color = MutedBurgundy
                                            )
                                        }

                                        witness.inconsistencies.forEach { inc ->
                                            Text(
                                                text = "• $inc",
                                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                                color = TextPrimary,
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                2 -> { // Timeline
                    Text(
                        text = "Chronological Timeline of Events",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = InkBlack
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    caseData.timeline.forEach { event ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, CardBorderBrass, RoundedCornerShape(10.dp)),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = event.time,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    ),
                                    color = ParchmentSurface,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (event.isContested) MutedBurgundy else InkBlack)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = event.title,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            ),
                                            color = InkBlack
                                        )
                                        if (event.isContested) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "(Contested)",
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                                color = MutedBurgundy,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(2.dp))

                                    Text(
                                        text = event.description,
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                        color = TextPrimary
                                    )

                                    Text(
                                        text = "Location: ${event.location}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                3 -> { // Personal Notes & AI Deduction Coach
                    Text(
                        text = "Personal Notes & AI Deduction Coach",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = InkBlack
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Formulate your hypothesis and request asynchronous reasoning feedback from the AI Deduction Coach.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = noteTextState,
                        onValueChange = { noteTextState = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        placeholder = { Text("Type your case hypothesis or personal investigation notes here...", fontSize = 13.sp) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = ParchmentSurface,
                                    focusedTextColor = InkBlack,
                                    unfocusedTextColor = InkBlack,
                                    cursorColor = InkBlack,
                                    focusedPlaceholderColor = InkBlack,
                                    unfocusedPlaceholderColor = InkBlack,
                            unfocusedContainerColor = ParchmentSurface,
                            focusedBorderColor = InkBlack,
                            unfocusedBorderColor = CardBorderBrass
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // AI Privacy & Disclosure Warning
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, CardBorderBrass, RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = InkBlack.copy(alpha = 0.05f))
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = DeepOxblood)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Disclosure: Submitting your hypothesis sends your text and discovered evidence IDs to an AI reasoning provider. Do not include sensitive personal info.",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 15.sp),
                                color = TextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onSaveNote(noteTextState) },
                            modifier = Modifier.weight(1f).height(46.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = InkBlack, contentColor = ParchmentSurface),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = null, tint = RestrainedGold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Save Note", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = { onRequestAiReview(noteTextState) },
                            enabled = !isAiLoading && noteTextState.isNotBlank(),
                            modifier = Modifier.weight(1f).height(46.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = DeepOxblood, contentColor = WarmParchment),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            if (isAiLoading) {
                                CircularProgressIndicator(color = ParchmentSurface, modifier = Modifier.size(20.dp))
                            } else {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = RestrainedGold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Review Reasoning", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }

                    val activeAiFb = aiResult?.takeIf { it.caseId == caseData.id }
                        ?: com.example.finalappmoreforensicsandfiction.data.AiFeedbackStorage.decode(savedAiFbText)?.takeIf { it.caseId == caseData.id }
                    if (activeAiFb == null && !savedAiFbText.isNullOrBlank()) {
                        Text("Previously saved feedback", color = ParchmentSurface)
                        Text(savedAiFbText, color = ParchmentSurface)
                    }

                    activeAiFb?.let { fb ->
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.5.dp, DeepOxblood, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = DeepOxblood)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Deduction Coach AI Review",
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                            color = DeepOxblood
                                        )
                                    }

                                    Text(
                                        text = if (fb.isDemoResponse) "Demo Response" else "Live AI",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = ParchmentSurface,
                                        modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(DeepOxblood).padding(4.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(text = "1. Evidence Supporting Your Reasoning:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = CorrectGreen)
                                fb.supportingEvidence.forEach { s ->
                                    Text(text = "• $s", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = TextPrimary, modifier = Modifier.padding(top = 2.dp))
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(text = "2. Assumptions to Reconsider:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = MutedBurgundy)
                                fb.assumptionsToReconsider.forEach { a ->
                                    Text(text = "• $a", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = TextPrimary, modifier = Modifier.padding(top = 2.dp))
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(text = "3. What Remains Uncertain:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = TextSecondary)
                                fb.remainingUncertainties.forEach { u ->
                                    Text(text = "• $u", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = TextPrimary, modifier = Modifier.padding(top = 2.dp))
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Card(
                                    modifier = Modifier.fillMaxWidth().border(1.dp, CardBorderBrass, RoundedCornerShape(8.dp)),
                                    colors = CardDefaults.cardColors(containerColor = InkBlack)
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(text = "Question to Help You Think:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp), color = RestrainedGold)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(text = fb.guidingQuestion, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium), color = ParchmentSurface)
                                    }
                                }
                            }
                        }
                    }
                }

                4 -> { // Ask AI Tab (Conversational Assistant with Question Modes)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Ask the Casebook",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                                    color = InkBlack
                                )
                                Text(
                                    text = "Conversational assistant powered by Gemini. Educational information only.",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = TextSecondary
                                )
                            }

                            if (chatMessages.isNotEmpty()) {
                                OutlinedButton(
                                    onClick = { showClearChatDialog = true },
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = DeepOxblood, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Clear", fontSize = 11.sp, color = DeepOxblood)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Question Mode Selector Chips ("Case Evidence", "Forensics", "Thai Law")
                        Text(
                            text = "Question Mode:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                            color = AntiqueBrass
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val modes = listOf("Case Evidence", "Forensics", "Thai Law")
                            items(modes) { m ->
                                val isSelected = selectedChatModeState == m
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedChatModeState = m },
                                    label = { Text(m, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = DeepOxblood,
                                        selectedLabelColor = WarmParchment,
                                        containerColor = ParchmentSurface,
                                        labelColor = TextPrimary
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Suggested Questions Chips per Mode
                        Text(
                            text = "Suggested Questions ($selectedChatModeState):",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                            color = AntiqueBrass
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(suggestedQuestions) { sq ->
                                FilterChip(
                                    selected = false,
                                    onClick = {
                                        chatInputState = sq
                                    },
                                    label = { Text(sq, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = ParchmentSurface,
                                        labelColor = TextPrimary
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Message Bubbles Area
                        if (chatMessages.isEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = DeepOxblood, modifier = Modifier.size(32.dp))
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "No conversation yet in [$selectedChatModeState] mode.\nAsk a question below.",
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                        color = TextSecondary,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                chatMessages.forEach { msg ->
                                    val isUser = msg.role == "user"
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                                    ) {
                                        Card(
                                            modifier = Modifier.fillMaxWidth(0.85f),
                                            shape = RoundedCornerShape(10.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (isUser) InkBlack else ParchmentSurface
                                            ),
                                            border = if (!isUser) androidx.compose.foundation.BorderStroke(1.dp, CardBorderBrass) else null
                                        ) {
                                            Column(modifier = Modifier.padding(12.dp)) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = if (isUser) "Investigator" else "Ask the Casebook (${msg.mode})",
                                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                                                        color = if (isUser) RestrainedGold else DeepOxblood
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = msg.text,
                                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 18.sp),
                                                    color = if (isUser) ParchmentSurface else TextPrimary
                                                )

                                                // Render Tappable Source References if present
                                                if (msg.references.isNotEmpty()) {
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                    Text(
                                                        text = "Tappable Source References:",
                                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                                                        color = AntiqueBrass
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                        msg.references.forEach { refId ->
                                                            val matchEv = caseData.hotspots.find { it.id == refId }
                                                            val label = matchEv?.title ?: refId
                                                            OutlinedButton(
                                                                onClick = {
                                                                    selectedSourceRefModalId = refId
                                                                },
                                                                shape = RoundedCornerShape(4.dp),
                                                                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                                                            ) {
                                                                Text(label, fontSize = 10.sp, color = InkBlack)
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Error Feedback & Retry
                        chatErrorMessage?.let { err ->
                            Spacer(modifier = Modifier.height(10.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = MutedBurgundy.copy(alpha = 0.1f)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MutedBurgundy)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = RestrainedGold)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = "Chat Error:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = ParchmentSurface)
                                        Text(text = err, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = ParchmentSurface)
                                    }
                                    OutlinedButton(
                                        onClick = { sendChatQuestion() },
                                        enabled = !isChatLoading && chatInputState.isNotBlank(),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ParchmentSurface),
                                        shape = RoundedCornerShape(6.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = RestrainedGold, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text("Retry", fontSize = 11.sp, color = ParchmentSurface)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Question Input Field & Send Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = chatInputState,
                                onValueChange = {
                                    chatInputState = it
                                    chatStore.saveDraft(it)
                                },
                                modifier = Modifier.weight(1f),
                                placeholder = { Text("Ask the Casebook [$selectedChatModeState]...", fontSize = 12.sp) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = ParchmentSurface,
                                    unfocusedContainerColor = ParchmentSurface,
                                    focusedBorderColor = InkBlack,
                                    unfocusedBorderColor = CardBorderBrass
                                ),
                                shape = RoundedCornerShape(8.dp),
                                maxLines = 3
                            )

                            Button(
                                onClick = { sendChatQuestion() },
                                enabled = !isChatLoading && chatInputState.isNotBlank(),
                                colors = ButtonDefaults.buttonColors(containerColor = DeepOxblood, contentColor = WarmParchment),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(52.dp)
                            ) {
                                if (isChatLoading) {
                                    CircularProgressIndicator(color = ParchmentSurface, modifier = Modifier.size(20.dp))
                                } else {
                                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = RestrainedGold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Educational Legal Notice
                        Text(
                            text = "Educational information only. This is not legal advice.",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // Clear Chat Confirmation Dialog
        if (showClearChatDialog) {
            AlertDialog(
                onDismissRequest = { showClearChatDialog = false },
                title = { Text("Clear Chat History?", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to delete the conversational chat history for this case?", fontSize = 13.sp) },
                confirmButton = {
                    Button(
                        onClick = {
                            chatMessages = emptyList()
                            chatStore.save(emptyList())
                            showClearChatDialog = false
                            Toast.makeText(context, "Chat history cleared.", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MutedBurgundy)
                    ) {
                        Text("Clear History", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showClearChatDialog = false }) {
                        Text("Cancel")
                    }
                },
                containerColor = ParchmentSurface
            )
        }

        // Source Reference Details Modal Sheet
        selectedSourceRefModalId?.let { refId ->
            val matchEv = caseData.hotspots.find { it.id == refId }
            if (matchEv != null) {
                ModalBottomSheet(
                    onDismissRequest = { selectedSourceRefModalId = null },
                    sheetState = sheetState,
                    containerColor = ParchmentSurface
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        EvidenceCard(
                            evidence = matchEv,
                            isCollected = collectedIds.contains(refId),
                            onCollectClick = {}
                        )
                    }
                }
            } else if (CasesProvider.allForensicConcepts.any { it.id == refId }) {
                val lesson = CasesProvider.allForensicConcepts.first { it.id == refId }
                ModalBottomSheet(onDismissRequest = { selectedSourceRefModalId = null }, sheetState = sheetState, containerColor = ParchmentSurface) {
                    Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(16.dp)) {
                        Text(lesson.title, color = InkBlack, fontWeight = FontWeight.Bold)
                        Text(lesson.detailedExplanation, color = InkBlack)
                        Spacer(Modifier.height(12.dp))
                        Text("Limitations", color = InkBlack, fontWeight = FontWeight.Bold)
                        Text(lesson.scientificLimitations, color = InkBlack)
                    }
                }
            } else {
                val legalTopic = CasesProvider.allLegalTopics.find { it.lawTitle.contains(refId, true) || it.sectionArticle.contains(refId, true) }
                ModalBottomSheet(
                    onDismissRequest = { selectedSourceRefModalId = null },
                    sheetState = sheetState,
                    containerColor = ParchmentSurface
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        if (legalTopic != null) {
                            com.example.finalappmoreforensicsandfiction.ui.components.LegalSourceCard(legalSource = legalTopic)
                        } else {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = ParchmentSurface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderBrass)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = "Official Legal Source Reference", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = InkBlack)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Source Reference ID: $refId", fontSize = 12.sp, color = TextSecondary)
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(text = "Official source — Thai (Office of the Council of State)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MutedBurgundy)
                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedButton(
                                        onClick = {
                                            try {
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.krisdika.go.th"))
                                                context.startActivity(intent)
                                            } catch (_: Exception) {
                                                Toast.makeText(context, "Official source currently unavailable.", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(imageVector = Icons.AutoMirrored.Filled.Launch, contentDescription = null, tint = DeepOxblood, modifier = Modifier.padding(end = 6.dp))
                                        Text("Open Official Source", color = DeepOxblood, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    OutlinedButton(
                                        onClick = {
                                            try {
                                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                val clip = ClipData.newPlainText("Official Legal Source URL", "https://www.krisdika.go.th")
                                                clipboard.setPrimaryClip(clip)
                                                Toast.makeText(context, "URL copied to clipboard.", Toast.LENGTH_SHORT).show()
                                            } catch (_: Exception) {
                                                Toast.makeText(context, "Unable to copy link.", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = InkBlack, modifier = Modifier.padding(end = 6.dp))
                                        Text("Copy Link", color = InkBlack, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
