const fs=require('fs');const root='app/src/main/java/com/example/finalappmoreforensicsandfiction/';let v=fs.readFileSync(root+'viewmodel/MainViewModel.kt','utf8').replace(/\r\n/g,'\n');v=v.replaceAll('        _selectedCase.value = caseData\n        repository.setActiveCaseId(caseData.id)','        selectCase(caseData)');fs.writeFileSync(root+'viewmodel/MainViewModel.kt',v);
let n=fs.readFileSync(root+'ui/screens/NotebookScreen.kt','utf8').replace(/\r\n/g,'\n');n=n.replace('            } else {\n                val legalTopic =',`            } else if (CasesProvider.allForensicConcepts.any { it.id == refId }) {
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
                val legalTopic =`);fs.writeFileSync(root+'ui/screens/NotebookScreen.kt',n);
