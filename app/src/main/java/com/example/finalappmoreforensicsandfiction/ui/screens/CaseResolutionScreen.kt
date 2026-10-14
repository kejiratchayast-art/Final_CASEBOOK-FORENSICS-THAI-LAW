package com.example.finalappmoreforensicsandfiction.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderGold
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepNavy
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary

@Composable
fun CaseResolutionScreen(
    caseData: CaseData,
    userState: UserProgressState,
    onBackClick: () -> Unit,
    onRevealResolution: () -> Unit,
    onCompleteAndReturnHome: () -> Unit,
    onReplayCase: () -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val isBookmarked = userState.bookmarkedCaseIds.contains(caseData.id)
    val isResolutionRevealed = userState.revealedResolutionCaseIds.contains(caseData.id)
    var showSpoilerDialogState by remember { mutableStateOf(false) }

    val res = caseData.resolution

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HeaderBar(
            title = "Case Resolution: ${caseData.title}",
            onBackClick = onBackClick,
            isBookmarked = isBookmarked,
            onBookmarkToggle = onBookmarkToggle
        )

        LinearProgressIndicator(
            progress = { 1.0f },
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = CorrectGreen,
            trackColor = CardBorderGold
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Success Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, if (isResolutionRevealed) CorrectGreen else RestrainedGold, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DeepNavy)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = if (isResolutionRevealed) Icons.Default.EmojiEvents else Icons.Default.Lock,
                        contentDescription = null,
                        tint = RestrainedGold,
                        modifier = Modifier.height(40.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isResolutionRevealed) "Case Resolution Uncovered" else "Case Resolution Protected (Spoiler Warning)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        ),
                        color = ParchmentSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isResolutionRevealed) "Educational evaluation & resolution reviewed." else "Tap below to reveal the author's narrative reconstruction and evidence analysis.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = RestrainedGold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!isResolutionRevealed) {
                // Resolution Locked Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            tint = DeepNavy,
                            modifier = Modifier.height(36.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Resolution Hidden",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                            color = DeepNavy
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "To preserve the mystery and challenge, the full case resolution is hidden until explicitly revealed.",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { showSpoilerDialogState = true },
                            colors = ButtonDefaults.buttonColors(containerColor = DeepNavy, contentColor = ParchmentSurface),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = RestrainedGold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Reveal Resolution", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            } else {
                // 1. What Happened (Reconstruction)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = CorrectGreen)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "What Happened (Author Narrative Truth)",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                color = DeepNavy
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = res.reconstructionText,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 19.sp),
                            color = TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 2. What Evidence Supports
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "What the Evidence Supports",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                            color = DeepNavy
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = res.evidenceSupportedConclusions,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 18.sp),
                            color = TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 3. What Remains Uncertain (Limitations)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MutedBurgundy.copy(alpha = 0.05f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = MutedBurgundy)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "What Remains Uncertain (Unresolved Limitations)",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                color = MutedBurgundy
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = res.remainingUncertainties,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 18.sp),
                            color = TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 4. Key Takeaways
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Science, contentDescription = null, tint = DeepNavy)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Forensic Science Lessons Learned:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                color = DeepNavy
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        res.forensicTakeaways.forEach { takeaway ->
                            Text(
                                text = "• $takeaway",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                                color = TextPrimary,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = MutedBurgundy)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Thai Legal Lessons Learned:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                color = MutedBurgundy
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        res.legalTakeaways.forEach { takeaway ->
                            Text(
                                text = "• $takeaway",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                                color = TextPrimary,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DisclaimerBanner()

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onReplayCase,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Replay, contentDescription = null, tint = ParchmentSurface)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Replay Case", color = ParchmentSurface, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Button(
                    onClick = onCompleteAndReturnHome,
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CorrectGreen, contentColor = ParchmentSurface),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = ParchmentSurface)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Finish & Home", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // Spoiler Alert Dialog
        if (showSpoilerDialogState) {
            AlertDialog(
                onDismissRequest = { showSpoilerDialogState = false },
                title = { Text("Reveal Case Resolution?", fontWeight = FontWeight.Bold) },
                text = { Text("Are you ready to reveal the author's fictional reconstruction and evidence analysis? This will uncover story truths.", fontSize = 13.sp) },
                confirmButton = {
                    Button(
                        onClick = {
                            onRevealResolution()
                            showSpoilerDialogState = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DeepNavy, contentColor = ParchmentSurface)
                    ) {
                        Text("Reveal Resolution", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showSpoilerDialogState = false }) {
                        Text("Keep Hidden")
                    }
                },
                containerColor = ParchmentSurface,
                titleContentColor = TextPrimary,
                textContentColor = TextPrimary
            )
        }
    }
}
