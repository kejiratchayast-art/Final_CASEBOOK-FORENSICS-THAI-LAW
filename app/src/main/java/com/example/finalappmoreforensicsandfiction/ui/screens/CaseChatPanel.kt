package com.example.finalappmoreforensicsandfiction.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.finalappmoreforensicsandfiction.service.*
import com.example.finalappmoreforensicsandfiction.ui.theme.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun CaseChatPanel(caseId: String, discoveredIds: Set<String>, onSource: (String) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val store = remember(caseId) { ChatStore(context, caseId) }
    var messages by remember(caseId) { mutableStateOf(store.load()) }
    var draft by remember(caseId) { mutableStateOf(store.draft()) }
    var mode by androidx.compose.runtime.saveable.rememberSaveable(caseId) { mutableStateOf("Case Evidence") }
    var busy by remember(caseId) { mutableStateOf(false) }
    var error by remember(caseId) { mutableStateOf<String?>(null) }
    var clear by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val list = rememberLazyListState()
    LaunchedEffect(messages.size) { if (messages.isNotEmpty()) list.animateScrollToItem(messages.lastIndex) }

    fun send() {
        if (busy || draft.isBlank()) return
        val question = draft.trim()
        val requestMode = mode
        val history = messages
        busy = true; error = null
        scope.launch {
            try {
                val reply = CasebookChat.ask(caseId, requestMode, question, history, discoveredIds)
                messages = (history + ChatMessage("user", question, requestMode) + reply).takeLast(60)
                store.save(messages); draft = ""; store.saveDraft("")
            } catch (e: CancellationException) { throw e }
            catch (e: Exception) { error = e.message?.takeIf { it.isNotBlank() } ?: "AI unavailable. Your draft is saved. Please retry." }
            finally { busy = false }
        }
    }
    Column(modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row {
            Text("Ask the Casebook", style = MaterialTheme.typography.titleMedium, color = MainText, modifier = Modifier.weight(1f))
            TextButton(onClick = { clear = true }, enabled = !busy && messages.isNotEmpty()) { Text("Clear", color = WarmHighlight) }
        }
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("Case Evidence", "Forensics", "Thai Law").forEach { label ->
                FilterChip(selected = mode == label, onClick = { mode = label }, enabled = !busy,
                    label = { Text(label) }, colors = FilterChipDefaults.filterChipColors(
                        containerColor = DarkSurface, labelColor = MainText,
                        selectedContainerColor = WineRed, selectedLabelColor = MainText))
            }
        }
        Text("Questions and selected context are sent to Gemini. Educational use only; not legal or medical advice.", color = SecondaryText, style = MaterialTheme.typography.bodySmall)
        LazyColumn(state = list, modifier = Modifier.weight(1f).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (messages.isEmpty()) item { Text("Ask about evidence, forensic terms, or missing legal information.", color = MainText) }
            items(messages) { message ->
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = if (message.role == "user") WineRed else DarkSurface)) {
                    Column(Modifier.padding(12.dp)) {
                        Text(if (message.role == "user") "You" else "Gemini · ${message.mode}", color = WarmHighlight, style = MaterialTheme.typography.labelMedium)
                        Text(message.text, color = MainText)
                        message.references.forEach { id -> TextButton(onClick = { onSource(id) }) { Text("Source: $id", color = WarmHighlight) } }
                    }
                }
            }
        }
        if (messages.isEmpty()) TextButton(onClick = {
            draft = if (mode == "Thai Law") "What legal information is still unverified?" else "What are the limitations of this forensic finding?"
            store.saveDraft(draft)
        }, enabled = !busy) { Text("Try a suggested question", color = WarmHighlight) }
        error?.let {
            Card(colors = CardDefaults.cardColors(containerColor = WineRed)) {
                Text(it, color = MainText, modifier = Modifier.padding(10.dp), style = MaterialTheme.typography.bodySmall)
            }
        }
        if (busy) LinearProgressIndicator(Modifier.fillMaxWidth(), color = WarmHighlight)
        OutlinedTextField(value = draft, onValueChange = { if (it.length <= 1000) { draft = it; store.saveDraft(it) } },
            modifier = Modifier.fillMaxWidth(), enabled = !busy, minLines = 1, maxLines = 3,
            label = { Text("Your question") }, colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = PaperSurface, unfocusedContainerColor = PaperSurface,
                focusedTextColor = PaperText, unfocusedTextColor = PaperText, cursorColor = PaperText,
                focusedLabelColor = WarmHighlight, unfocusedLabelColor = MainText,
                disabledContainerColor = PaperSurface, disabledTextColor = PaperText))
        Button(onClick = { send() }, enabled = !busy && draft.isNotBlank(), modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SelectedWineSurface, contentColor = MainText,
                disabledContainerColor = RaisedSurface, disabledContentColor = SecondaryText)) {
            Text(if (busy) "Asking Gemini…" else if (error != null) "Retry question" else "Send question")
        }
    }
    if (clear) AlertDialog(onDismissRequest = { clear = false }, title = { Text("Clear this case's chat?") },
        text = { Text("Messages will be removed. Notes and your draft remain.") },
        confirmButton = { TextButton(onClick = { messages = emptyList(); store.save(messages); clear = false }) { Text("Clear chat") } },
        dismissButton = { TextButton(onClick = { clear = false }) { Text("Cancel") } })
}
