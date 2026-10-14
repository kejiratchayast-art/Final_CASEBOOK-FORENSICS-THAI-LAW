package com.example.finalappmoreforensicsandfiction.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.components.ModeSwitchChip
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.BrassLight
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.DarkWalnut
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary
import com.example.finalappmoreforensicsandfiction.ui.theme.WarmParchment

@Composable
fun CaseOverviewScreen(
    caseData: CaseData,
    userState: UserProgressState,
    onBackClick: () -> Unit,
    onStartCase: () -> Unit,
    onOpenAskAi: () -> Unit,
    onOpenBodyExamination: () -> Unit,
    onModeChanged: (AppMode) -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val isBookmarked = userState.bookmarkedCaseIds.contains(caseData.id)

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HeaderBar(
            title = "Case ${caseData.caseNumber} Dossier Title Page",
            onBackClick = onBackClick,
            isBookmarked = isBookmarked,
            onBookmarkToggle = onBookmarkToggle,
            activeMode = userState.activeMode,
            onModeChanged = onModeChanged
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Leather-bound Dossier Title Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.8f)
                    .clip(RoundedCornerShape(14.dp))
                    .border(2.dp, CardBorderBrass, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkWalnut)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.BottomStart)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FolderSpecial,
                                contentDescription = null,
                                tint = AntiqueBrass,
                                modifier = Modifier.padding(end = 6.dp)
                            )
                            Text(
                                text = "DOSSIER REF: ${caseData.dossierId} — FICTIONAL CASE STUDY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = AntiqueBrass
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = caseData.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = WarmParchment
                        )
                        Text(
                            text = caseData.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = BrassLight
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clip(CircleShape)
                            .background(DeepOxblood)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = null,
                            tint = WarmParchment
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Metadata Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AntiqueBrass
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Difficulty: ${caseData.difficulty}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        ),
                        color = InkBlack
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "~${caseData.estimatedMinutes} mins duration",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Prominent "Ask AI" Button on Case Overview
            Button(
                onClick = onOpenAskAi,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = InkBlack,
                    contentColor = WarmParchment
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = AntiqueBrass
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ask AI Assistant (Case Dossier)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Prominent "Body Examination" Button on Case Overview
            Button(
                onClick = onOpenBodyExamination,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepOxblood,
                    contentColor = WarmParchment
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_section_autopsy),
                    contentDescription = null,
                    tint = AntiqueBrass,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Body Examination (Medical Atlas)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Mode Selector Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Select Experience Mode:",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        ),
                        color = InkBlack
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ModeSwitchChip(
                        currentMode = userState.activeMode,
                        onModeChanged = onModeChanged,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = userState.activeMode.description,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Premise Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Fictional Case Premise",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        color = DeepOxblood
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = caseData.premise,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        ),
                        color = TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Learning Objectives Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Measurable Learning Objectives",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        color = InkBlack
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    caseData.learningObjectives.forEach { objective ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = AntiqueBrass,
                                modifier = Modifier.padding(end = 6.dp, top = 2.dp)
                            )
                            Text(
                                text = objective,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                ),
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            DisclaimerBanner()

            Spacer(modifier = Modifier.height(18.dp))

            // Start Button (Deep Oxblood)
            Button(
                onClick = onStartCase,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
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
                    text = "Open Dossier & Start Chapter 1",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}
