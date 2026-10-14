package com.example.finalappmoreforensicsandfiction.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.R
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.EvidenceHotspot
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.EvidenceCard
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.scene.Case2DSceneCanvas
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestigationSceneScreen(
    caseData: CaseData,
    userState: UserProgressState,
    onBackClick: () -> Unit,
    onCollectEvidence: (String) -> Unit,
    onOpenNotebook: () -> Unit,
    onOpenAskAi: () -> Unit,
    onOpenBodyExamination: () -> Unit,
    onToggleHint: () -> Unit,
    onNavigateToActivities: () -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val collectedIds = userState.collectedEvidenceMap[caseData.id] ?: emptySet()
    var selectedHotspot by remember { mutableStateOf<EvidenceHotspot?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isBookmarked = userState.bookmarkedCaseIds.contains(caseData.id)

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HeaderBar(
            title = "2D Investigation Scene: ${caseData.title}",
            onBackClick = onBackClick,
            isBookmarked = isBookmarked,
            onBookmarkToggle = onBookmarkToggle
        )

        LinearProgressIndicator(
            progress = { 0.40f },
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = MutedBurgundy,
            trackColor = CardBorderBrass
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // 2D Scene Canvas with Hint Toggle!
            Case2DSceneCanvas(
                sceneType = caseData.sceneType,
                hotspots = caseData.hotspots,
                collectedHotspotIds = collectedIds,
                selectedHotspot = selectedHotspot,
                showLocationsHint = userState.showHotspotLocationsHint,
                onToggleHint = onToggleHint,
                onHotspotSelect = { hotspot ->
                    selectedHotspot = hotspot
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DisclaimerBanner()

            Spacer(modifier = Modifier.height(16.dp))

            // Prominent "Ask AI" & "Body Examination" Buttons on Investigation Scene
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onOpenAskAi,
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InkBlack,
                        contentColor = ParchmentSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = RestrainedGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Ask AI",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = onOpenBodyExamination,
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepOxblood,
                        contentColor = ParchmentSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_section_autopsy),
                        contentDescription = null,
                        tint = AntiqueBrass,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Body Exam",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenNotebook,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = InkBlack
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Notebook (${collectedIds.size}/${caseData.hotspots.size})",
                        color = InkBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }

                Button(
                    onClick = onNavigateToActivities,
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepOxblood,
                        contentColor = ParchmentSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Activities",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = RestrainedGold
                    )
                }
            }
        }

        // Evidence Hotspot Detail Modal Sheet
        selectedHotspot?.let { hotspot ->
            val isCollected = collectedIds.contains(hotspot.id)

            ModalBottomSheet(
                onDismissRequest = { selectedHotspot = null },
                sheetState = sheetState,
                containerColor = ParchmentSurface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    EvidenceCard(
                        evidence = hotspot,
                        isCollected = isCollected,
                        onCollectClick = {
                            onCollectEvidence(hotspot.id)
                        }
                    )
                }
            }
        }
    }
}
