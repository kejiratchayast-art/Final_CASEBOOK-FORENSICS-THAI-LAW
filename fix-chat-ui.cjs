const fs=require('fs');
const root='app/src/main/java/com/example/finalappmoreforensicsandfiction/';
let a=fs.readFileSync(root+'ui/screens/ActivityScreen.kt','utf8');
a=a.replace('outlinedButtonColors(contentColor = InkBlack)','outlinedButtonColors(contentColor = ParchmentSurface)').replace('Text("Previous Activity", fontWeight = FontWeight.Bold, color = InkBlack)','Text("Previous Activity", fontWeight = FontWeight.Bold, color = ParchmentSurface)');
fs.writeFileSync(root+'ui/screens/ActivityScreen.kt',a);
let s=fs.readFileSync(root+'ui/screens/NotebookScreen.kt','utf8');
s=s.replace('import androidx.compose.runtime.Composable','import kotlinx.coroutines.CancellationException\nimport androidx.compose.foundation.layout.imePadding\nimport androidx.compose.foundation.layout.heightIn\nimport androidx.compose.runtime.Composable');
const helper=`
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
`;
s=s.replace('    val suggestedQuestions =',helper+'\n    val suggestedQuestions =');
let start=s.indexOf('                                    OutlinedButton(',s.indexOf('// Error Feedback & Retry'));
let click=s.indexOf('onClick = {',start);
let end=s.indexOf('                                        shape =',click);
s=s.slice(0,click)+'onClick = { sendChatQuestion() },\n                                        enabled = !isChatLoading && chatInputState.isNotBlank(),\n                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ParchmentSurface),\n'+s.slice(end);
start=s.indexOf('                            Button(',s.indexOf('// Chat Input Area'));
if(start<0) start=s.indexOf('                            Button(',s.indexOf('placeholder = { Text("Ask the Casebook'));
click=s.indexOf('onClick = {',start);
end=s.indexOf('                                enabled =',click);
s=s.slice(0,click)+'onClick = { sendChatQuestion() },\n'+s.slice(end);
// Error card is on dark surface, so foreground must be light.
const ea=s.indexOf('// Error Feedback & Retry');
const eb=s.indexOf('// Chat Input Area',ea)>0?s.indexOf('// Chat Input Area',ea):s.indexOf('placeholder = { Text("Ask the Casebook',ea);
let err=s.slice(ea,eb).replace(/color = TextPrimary/g,'color = ParchmentSurface').replace(/color = MutedBurgundy/g,'color = ParchmentSurface').replace(/tint = MutedBurgundy/g,'tint = RestrainedGold').replace(/tint = DeepOxblood/g,'tint = RestrainedGold').replace(/color = DeepOxblood/g,'color = ParchmentSurface');
s=s.slice(0,ea)+err+s.slice(eb);
s=s.replace('focusedContainerColor = ParchmentSurface,','focusedContainerColor = ParchmentSurface,\n                                    focusedTextColor = InkBlack,\n                                    unfocusedTextColor = InkBlack,\n                                    cursorColor = InkBlack,\n                                    focusedPlaceholderColor = InkBlack,\n                                    unfocusedPlaceholderColor = InkBlack,');
s=s.replace('var noteTextState by remember(existingNote)','var noteTextState by androidx.compose.runtime.saveable.rememberSaveable(caseData.id, existingNote)');
s=s.replace('Column(modifier = modifier.fillMaxSize()', 'Column(modifier = modifier.fillMaxSize().imePadding()');
fs.writeFileSync(root+'ui/screens/NotebookScreen.kt',s);
let env=fs.readFileSync('backend/.env','utf8');
env=env.replace(/^GEMINI_MODEL=.*$/m,'GEMINI_MODEL=gemini-3.5-flash-lite');
fs.writeFileSync('backend/.env',env);
let server=fs.readFileSync('backend/server.js','utf8');
server=server.replaceAll("'gemini-3.8-flash'","'gemini-3.5-flash-lite'").replaceAll(', thinkingConfig: { thinkingBudget: 0 }','');
server=server.replaceAll('await fetch(`','await fetchWithRetry(`');
server=server.replace("const counts = new Map();",`const counts = new Map();
async function fetchWithRetry(url, options) {
  for (let attempt = 0; attempt < 2; attempt++) {
    try {
      const response = await fetch(url, { ...options, signal: AbortSignal.timeout(12000) });
      if (attempt === 0 && [502,503,504].includes(response.status)) {
        await response.text();
        await new Promise(resolve => setTimeout(resolve, 500));
        continue;
      }
      return response;
    } catch (error) {
      if (attempt === 1) throw error;
    }
  }
}
`);
server=server.replace("const answer = chatData.answer || 'Consult the case evidence and legal briefs.';",`if (typeof chatData.answer !== 'string' || !chatData.answer.trim() || !Array.isArray(chatData.referencedSourceIds)) throw new Error('Invalid model response');
    const answer = chatData.answer;`);
server=server.replace('...Object.keys(legalCatalogue)','...legalExcerpts.map(s => s.id)');
fs.writeFileSync('backend/server.js',server);
console.log('Updated Activities contrast, chat retry, draft handling, and provider configuration.');
