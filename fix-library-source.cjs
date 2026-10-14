const fs=require('fs');const p='app/src/main/java/com/example/finalappmoreforensicsandfiction/ui/screens/KnowledgeLibraryScreen.kt';let s=fs.readFileSync(p,'utf8').replace(/\r\n/g,'\n');const a=s.indexOf('                        Row(',s.indexOf('Text(text = legal.educationalExplanation'));const b=s.indexOf('                    }\n                }',a);if(a<0||b<0)throw Error('Source layout not found');s=s.slice(0,a)+`                        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Official source — Thai", color = InkBlack, fontWeight = FontWeight.Bold)
                            Text(legal.officialSourceTitle, color = InkBlack)
                            Text("Pending official-source verification", color = DeepOxblood, style = MaterialTheme.typography.bodySmall)
                            androidx.compose.material3.OutlinedButton(onClick = {
                                try { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(legal.officialSourceUrl))) }
                                catch (_: Exception) { Toast.makeText(context, "Cannot open external web browser", Toast.LENGTH_SHORT).show() }
                            }, modifier = Modifier.fillMaxWidth(), colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(contentColor = InkBlack)) {
                                Text("Open Official Source")
                            }
                        }
`+s.slice(b);fs.writeFileSync(p,s);
