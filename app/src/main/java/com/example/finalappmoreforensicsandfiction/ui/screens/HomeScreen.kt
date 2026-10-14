package com.example.finalappmoreforensicsandfiction.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.R
import com.example.finalappmoreforensicsandfiction.data.AppMode
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.CasesProvider
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.MainTab
import com.example.finalappmoreforensicsandfiction.ui.components.ModeSwitchChip
import com.example.finalappmoreforensicsandfiction.ui.components.VictorianStudyHeroIllustration
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.DarkWalnut
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary
import com.example.finalappmoreforensicsandfiction.ui.theme.WarmParchment

@Composable
fun HomeScreen(
    userState: UserProgressState,
    onCaseSelect: (CaseData) -> Unit,
    onOpenPreCaseBriefing: () -> Unit,
    onOpenLibrary: () -> Unit,
    onOpenSection: (MainTab) -> Unit,
    onModeChanged: (AppMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val activeCase = CasesProvider.allCases.find { it.id == userState.activeCaseId } ?: CasesProvider.case1

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmParchment)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // 1. Victorian Detective Study Cinematic Hero Illustration
        VictorianStudyHeroIllustration()

        Spacer(modifier = Modifier.height(14.dp))

        // 2. Readable Parchment Panel with Intro & Mode Selection
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Welcome to the Detective's Study",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = InkBlack
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Analyze physical trace evidence, review witness statements, and evaluate Thai criminal law across four interconnected bureau learning sections.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Mode Selector Switch
                Text(
                    text = "Experience Mode:",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    color = DeepOxblood
                )

                Spacer(modifier = Modifier.height(6.dp))

                ModeSwitchChip(
                    currentMode = userState.activeMode,
                    onModeChanged = onModeChanged,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 3. Primary Action: Full-Width Continue Learning Button
        val activeStep = userState.caseStepMap[activeCase.id] ?: 0
        val isCompleted = userState.completedCaseIds.contains(activeCase.id)

        Button(
            onClick = { onCaseSelect(activeCase) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepOxblood,
                contentColor = WarmParchment
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = AntiqueBrass
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isCompleted) "Review Case ${activeCase.caseNumber}: ${activeCase.title}" else "Continue Investigation: Case ${activeCase.caseNumber}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 4. Four Illustrated Section Entry Points
        Text(
            text = "Investigation Bureau Sections",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = InkBlack
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SectionEntryCard(
                title = "The Cases",
                subtitle = "Fictional Dossiers",
                iconResId = R.drawable.ic_section_cases,
                onClick = { onOpenSection(MainTab.CASES) },
                modifier = Modifier.weight(1f)
            )

            SectionEntryCard(
                title = "Autopsy",
                subtitle = "Medical Reports",
                iconResId = R.drawable.ic_section_autopsy,
                onClick = { onOpenSection(MainTab.LIBRARY) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SectionEntryCard(
                title = "Anatomy",
                subtitle = "Trauma Atlas",
                iconResId = R.drawable.ic_section_anatomy,
                onClick = { onOpenSection(MainTab.LIBRARY) },
                modifier = Modifier.weight(1f)
            )

            SectionEntryCard(
                title = "Thai Law",
                subtitle = "Statutory Library",
                iconResId = R.drawable.ic_section_law,
                onClick = { onOpenSection(MainTab.LIBRARY) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 5. Active Case Distinctive Dossier Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp))
                .clickable { onCaseSelect(activeCase) },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(DarkWalnut),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FolderSpecial,
                        contentDescription = null,
                        tint = AntiqueBrass
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "DOSSIER #${activeCase.caseNumber}: ${activeCase.title}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = InkBlack
                    )
                    Text(
                        text = activeCase.subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = TextSecondary,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    val progressFloat = if (isCompleted) 1.0f else (activeStep.toFloat() / 7f)
                    LinearProgressIndicator(
                        progress = { progressFloat },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = if (isCompleted) CorrectGreen else DeepOxblood,
                        trackColor = CardBorderBrass
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Resume Investigation",
                    tint = DeepOxblood
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 6. Pre-Case Briefing Access Card ("Before You Investigate")
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
                    tint = AntiqueBrass,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Before You Investigate (Civilian Safety)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        ),
                        color = WarmParchment
                    )
                    Text(
                        text = "Real-world scene safety, 1669 medical emergency aid, and evidence preservation guidelines.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = AntiqueBrass
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = AntiqueBrass
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 7. Case Library Section
        Text(
            text = "The Case Archive (Fictional Dossiers)",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = InkBlack
        )

        Spacer(modifier = Modifier.height(10.dp))

        CasesProvider.allCases.forEach { caseData ->
            val step = userState.caseStepMap[caseData.id] ?: 0
            val caseCompleted = userState.completedCaseIds.contains(caseData.id)

            CaseCardItem(
                caseData = caseData,
                currentStep = step,
                isCompleted = caseCompleted,
                onClick = { onCaseSelect(caseData) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Access to Knowledge Library Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp))
                .clickable { onOpenLibrary() },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = InkBlack)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocalLibrary,
                    contentDescription = null,
                    tint = AntiqueBrass,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Knowledge Library & Shared Glossary",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = WarmParchment
                    )
                    Text(
                        text = "Browse forensic principles, Thai statutory citations, and shared terminology.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = AntiqueBrass
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Open Knowledge Library",
                    tint = AntiqueBrass
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Concise Educational Footer Notice
        DisclaimerBanner()
    }
}

@Composable
fun SectionEntryCard(
    title: String,
    subtitle: String,
    iconResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.5.dp, CardBorderBrass, RoundedCornerShape(10.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = InkBlack)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                ),
                color = WarmParchment
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = AntiqueBrass
            )
        }
    }
}

@Composable
fun CaseCardItem(
    caseData: CaseData,
    currentStep: Int,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "DOSSIER: ${caseData.dossierId}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        color = WarmParchment,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(DeepOxblood)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Fictional Case Study",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        color = InkBlack,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AntiqueBrass.copy(alpha = 0.3f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = RestrainedGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = caseData.difficulty,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${caseData.estimatedMinutes} mins",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = caseData.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = InkBlack
            )

            Text(
                text = caseData.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                ),
                color = DeepOxblood
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = caseData.synopsis,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 13.sp,
                    lineHeight = 17.sp
                ),
                color = TextPrimary,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar & Action Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isCompleted) "Status: Case Concluded" else "Progress: Step $currentStep/7",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = if (isCompleted) CorrectGreen else TextSecondary,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    LinearProgressIndicator(
                        progress = { if (isCompleted) 1.0f else (currentStep.toFloat() / 7f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(2.5.dp)),
                        color = if (isCompleted) CorrectGreen else DeepOxblood,
                        trackColor = CardBorderBrass
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepOxblood,
                        contentColor = WarmParchment
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isCompleted) "Review Case" else "Open Dossier",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
