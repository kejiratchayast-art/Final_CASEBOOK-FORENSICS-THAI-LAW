package com.example.finalappmoreforensicsandfiction.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.CasesProvider
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary
import com.example.finalappmoreforensicsandfiction.ui.theme.WarmParchment

enum class LibrarySection(val label: String) {
    ALL("All Library"),
    FORENSICS("Forensics"),
    AUTOPSY("Autopsy"),
    ANATOMY("Anatomy"),
    LAW("Thai Law")
}

@Composable
fun KnowledgeLibraryScreen(
    onCaseSelect: (String) -> Unit,
    onOpenPreCaseBriefing: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var searchQueryState by remember { mutableStateOf("") }
    var selectedSectionState by remember { mutableStateOf(LibrarySection.ALL) }

    val forensicList = CasesProvider.allForensicConcepts.filter {
        (selectedSectionState == LibrarySection.ALL || selectedSectionState == LibrarySection.FORENSICS) &&
                (searchQueryState.isEmpty() || it.title.contains(searchQueryState, true) || it.summary.contains(searchQueryState, true))
    }

    val autopsyList = CasesProvider.allAutopsyReports.filter {
        (selectedSectionState == LibrarySection.ALL || selectedSectionState == LibrarySection.AUTOPSY) &&
                (searchQueryState.isEmpty() || it.victimName.contains(searchQueryState, true) || it.examDate.contains(searchQueryState, true))
    }

    val anatomyList = CasesProvider.allAnatomyLessons.filter {
        (selectedSectionState == LibrarySection.ALL || selectedSectionState == LibrarySection.ANATOMY) &&
                (searchQueryState.isEmpty() || it.title.contains(searchQueryState, true) || it.summary.contains(searchQueryState, true))
    }

    val legalList = CasesProvider.allLegalTopics.filter {
        (selectedSectionState == LibrarySection.ALL || selectedSectionState == LibrarySection.LAW) &&
                (searchQueryState.isEmpty() || it.lawTitle.contains(searchQueryState, true) || it.sectionArticle.contains(searchQueryState, true))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderBar(title = "Investigation Library & Reference Bureau")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "Bureau Reference Library",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = InkBlack
                )
                Text(
                    text = "Organized reference material covering Forensics, Autopsy, Anatomy, and Thai Law.",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = TextSecondary
                )
            }

            // Pre-Case Briefing Link Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorderBrass, RoundedCornerShape(10.dp))
                        .clickable { onOpenPreCaseBriefing() },
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = InkBlack)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = RestrainedGold,
                            modifier = Modifier.height(24.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Real-World Guidance: Before You Investigate",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                ),
                                color = WarmParchment
                            )
                            Text(
                                text = "Safety, 1669 emergency medical aid, and evidence preservation guidelines.",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = RestrainedGold
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = RestrainedGold
                        )
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQueryState,
                    onValueChange = { searchQueryState = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search library material, autopsy reports, anatomy, statutes...", fontSize = 13.sp) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = InkBlack) },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = ParchmentSurface,
                        unfocusedContainerColor = ParchmentSurface,
                        focusedBorderColor = InkBlack,
                        unfocusedBorderColor = CardBorderBrass
                    )
                )
            }

            // Filter Chips
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(LibrarySection.entries) { sec ->
                        val isSelected = selectedSectionState == sec
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSectionState = sec },
                            label = { Text(sec.label, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DeepOxblood,
                                selectedLabelColor = WarmParchment,
                                containerColor = ParchmentSurface,
                                labelColor = TextPrimary
                            )
                        )
                    }
                }
            }

            item { DisclaimerBanner() }

            // Forensics Items
            items(forensicList) { concept ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Science, contentDescription = null, tint = InkBlack)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = concept.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                                color = InkBlack,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Forensics",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = WarmParchment,
                                modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(DeepOxblood).padding(4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = concept.summary, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp), color = TextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = concept.detailedExplanation, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp), color = TextSecondary)
                    }
                }
            }

            // Autopsy Items
            items(autopsyList) { autopsy ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface),
                    onClick = { onCaseSelect(autopsy.caseId) }
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Assignment, contentDescription = null, tint = DeepOxblood)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Autopsy Report: ${autopsy.victimName}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                                color = InkBlack,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Autopsy",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = WarmParchment,
                                modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(DeepOxblood).padding(4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Exam Date: ${autopsy.examDate}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Toxicology: ${autopsy.toxicologySummary}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = TextPrimary)
                    }
                }
            }

            // Anatomy Items
            items(anatomyList) { anat ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = InkBlack)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = anat.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                                color = InkBlack,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Anatomy",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = WarmParchment,
                                modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(DeepOxblood).padding(4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = anat.summary, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp), color = TextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = anat.structuralDescription, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp), color = TextSecondary)
                    }
                }
            }

            // Legal Items
            items(legalList) { legal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Gavel, contentDescription = null, tint = DeepOxblood)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = legal.lawTitle,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                                    color = InkBlack
                                )
                                Text(
                                    text = legal.sectionArticle,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                                    color = DeepOxblood
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = legal.statutoryText, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp), color = TextPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = legal.educationalExplanation, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp), color = TextSecondary)

                        Spacer(modifier = Modifier.height(10.dp))

                        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                    }
                }
            }
        }
    }
}
