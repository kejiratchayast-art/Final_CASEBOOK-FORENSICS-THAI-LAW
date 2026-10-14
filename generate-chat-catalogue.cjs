const fs=require('fs');
const source=fs.readFileSync('app/src/main/java/com/example/finalappmoreforensicsandfiction/data/CasesProvider.kt','utf8');
const catalogue={};
for(let n=1;n<=3;n++) {
 const start=source.indexOf(`private val case${n}Hotspots`);
 const end=source.indexOf(`private val case${n}Witnesses`,start);
 const section=source.slice(start,end);
 const entries=[];
 for(const chunk of section.split('EvidenceHotspot(').slice(1)) {
  const id=chunk.match(/id = "([^"]+)"/)[1];
  const title=chunk.match(/title = "([^"]+)"/)[1];
  const facts=chunk.match(/observedFacts = listOf\(([\s\S]*?)\n\s*\)/)[1];
  const observedFacts=[...facts.matchAll(/"((?:[^"\\]|\\.)*)"/g)].map(m=>JSON.parse('"'+m[1]+'"'));
  entries.push({id,title,observedFacts});
 }
 catalogue[`case_0${n}`]=entries;
}
fs.writeFileSync('backend/chat-catalogue.json',JSON.stringify(catalogue,null,2));
console.log(Object.entries(catalogue).map(([id,e])=>`${id}: ${e.length} public observations`).join('\n'));
