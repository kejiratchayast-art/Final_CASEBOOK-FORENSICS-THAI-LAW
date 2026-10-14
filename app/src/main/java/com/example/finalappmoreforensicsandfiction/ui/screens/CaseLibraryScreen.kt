package com.example.finalappmoreforensicsandfiction.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.CasesProvider
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary
import com.example.finalappmoreforensicsandfiction.ui.theme.WarmParchment

@Composable
fun CaseLibraryScreen(
    userState: UserProgressState,
    onCaseSelect: (CaseData) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmParchment),
    ) {
        HeaderBar(title = "The Case Archive — Dossier Collection")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Text(
                    text = "Curated Investigation Dossiers",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    ),
                    color = InkBlack,
                )
                Text(
                    text = "Select any fictional investigation dossier below to begin or resume your deductive analysis.",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = TextSecondary,
                )
            }

            item { DisclaimerBanner() }

            items(CasesProvider.allCases) { caseData ->
                val step = userState.caseStepMap[caseData.id] ?: 0
                val isCompleted = userState.completedCaseIds.contains(caseData.id)

                CaseLibraryDossierCard(
                    caseData = caseData,
                    currentStep = step,
                    isCompleted = isCompleted,
                    onClick = {
                        onCaseSelect(caseData)
                    },
                )
            }
        }
    }
}

@Composable
fun CaseLibraryDossierCard(
    caseData: CaseData,
    currentStep: Int,
    isCompleted: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ParchmentSurface),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "DOSSIER: ${caseData.dossierId}",
                style = MaterialTheme.typography.labelMedium,
                color = WarmParchment,
                modifier = Modifier.clip(RoundedCornerShape(6.dp))
                    .background(DeepOxblood).padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Fictional case • ${caseData.difficulty} • ${caseData.estimatedMinutes} mins",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Case ${caseData.caseNumber}: ${caseData.title}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                ),
                color = InkBlack,
            )

            Text(
                text = caseData.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                ),
                color = DeepOxblood,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = caseData.synopsis,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                ),
                color = TextPrimary,
                maxLines = 3,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar & Action Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isCompleted) {
                            "Status: Case Concluded"
                        } else if (currentStep > 0) {
                            "Progress: Step $currentStep/7"
                        } else {
                            "Status: Unopened Dossier"
                        },
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = if (isCompleted) CorrectGreen else TextSecondary,
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    LinearProgressIndicator(
                        progress = { if (isCompleted) 1.0f else (currentStep.toFloat() / 7f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(2.5.dp)),
                        color = if (isCompleted) CorrectGreen else DeepOxblood,
                        trackColor = CardBorderBrass,
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepOxblood,
                        contentColor = WarmParchment,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                ) {
                    Text(
                        text = if (isCompleted) {
                            "Review Dossier"
                        } else if (currentStep > 0) {
                            "Continue"
                        } else {
                            "Begin Investigation"
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = AntiqueBrass)
                }
            }
        }
    }
}
