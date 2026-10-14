const fs=require('fs');const p='app/src/main/java/com/example/finalappmoreforensicsandfiction/ui/screens/NotebookScreen.kt';let s=fs.readFileSync(p,'utf8');const a=s.indexOf('        Column(',s.indexOf('// Scrollable Tabs'));s=s.slice(0,a)+`        if (selectedTabState == 4) {
            androidx.compose.runtime.key(caseData.id) {
                CaseChatPanel(caseData.id, collectedIds, { selectedSourceRefModalId = it }, Modifier.weight(1f).fillMaxWidth())
            }
        } else `+s.slice(a).trimStart();fs.writeFileSync(p,s);
