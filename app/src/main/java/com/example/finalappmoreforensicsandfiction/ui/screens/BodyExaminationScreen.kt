package com.example.finalappmoreforensicsandfiction.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.R
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.AmberHighlight
import com.example.finalappmoreforensicsandfiction.ui.theme.BurgundyAccent
import com.example.finalappmoreforensicsandfiction.ui.theme.CopperAccent
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepNavySurface
import com.example.finalappmoreforensicsandfiction.ui.theme.MidnightBackground
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedText
import com.example.finalappmoreforensicsandfiction.ui.theme.ReadingInk
import com.example.finalappmoreforensicsandfiction.ui.theme.ReadingPaperSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.WarmIvoryText

data class ExaminationMarker(
    val id: String,
    val viewName: String, // "Overview", "Front", "Back", "Left", "Right"
    val xRatio: Float,
    val yRatio: Float,
    val regionLabel: String,
    val observation: String,
    val interpretation: String,
    val uncertainty: String,
    val keyTakeaway: String,
    val relatedEvidenceId: String?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyExaminationScreen(
    caseData: CaseData,
    onBackClick: () -> Unit,
    onOpenAskAiWithPrompt: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedViewNameState by remember { mutableStateOf("Overview") }
    var showMarkersState by remember { mutableStateOf(true) }
    var selectedMarker by remember { mutableStateOf<ExaminationMarker?>(null) }
    var showAccessibleList by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Zoom and Pan state
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val context = LocalContext.current
    val bitmap = remember {
        try {
            BitmapFactory.decodeResource(context.resources, R.drawable.body_examination_reference)
        } catch (_: Exception) {
            null
        }
    }
    val imageBitmap = remember(bitmap) { bitmap?.asImageBitmap() }

    // Case-specific markers & examination type
    val examData = when (caseData.id) {
        "case_01" -> Triple(
            "Autopsy Examination (Deceased Subject Kitti)",
            "Post-mortem medical examination of the deceased subject establishing trauma and toxicology.",
            listOf(
                ExaminationMarker(
                    id = "m1",
                    viewName = "Back",
                    xRatio = 0.62f, // Back figure center in 4-view asset
                    yRatio = 0.28f,
                    regionLabel = "Occipital Scalp Region",
                    observation = "3.5 cm linear laceration over posterior occipital scalp region with underlying subarachnoid hemorrhage.",
                    interpretation = "Blunt force impact against a hard flat or curved structural barrier (e.g. balcony railing).",
                    uncertainty = "Cannot establish whether the impact was from an accidental trip or a deliberate push.",
                    keyTakeaway = "Posterior head impact indicates direct forceful contact prior to fall.",
                    relatedEvidenceId = "c1_h4"
                )
            )
        )
        "case_03" -> Triple(
            "Injury Examination (Living Pedestrian Victim)",
            "Clinical assessment of the living injured pedestrian following vehicular impact.",
            listOf(
                ExaminationMarker(
                    id = "m3",
                    viewName = "Front",
                    xRatio = 0.38f, // Front figure center in 4-view asset
                    yRatio = 0.80f,
                    regionLabel = "Right Lower Leg (Tibia/Fibula)",
                    observation = "Transverse comminuted bumper fracture of right tibia/fibula centered 42 cm above heel level.",
                    interpretation = "Direct lateral impact matching front bumper height of an elevated pickup truck.",
                    uncertainty = "Exact vehicle velocity requires skid mark physics and road friction coefficient correlation.",
                    keyTakeaway = "Fracture height correlates with vehicle bumper profile.",
                    relatedEvidenceId = "c3_h5"
                )
            )
        )
        else -> Triple(
            "General Anatomy Reference (No Case Injury Indicated)",
            "No case-specific body examination is indicated for this art gallery theft case. The diagram remains available as a General Anatomy Reference.",
            emptyList()
        )
    }

    val examTypeTitle = examData.first
    val examTypeNotice = examData.second
    val markers = examData.third

    val viewFilteredMarkers = if (selectedViewNameState == "Overview") {
        markers
    } else {
        markers.filter { it.viewName == selectedViewNameState }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBackground)
    ) {
        HeaderBar(
            title = "Body Examination: Case ${caseData.caseNumber}",
            onBackClick = onBackClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, CopperAccent, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepNavySurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = examTypeTitle,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                            color = WarmIvoryText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = examTypeNotice,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                            color = AmberHighlight
                        )
                    }
                }
            }

            item { DisclaimerBanner() }

            // View Selector & Controls Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // View Chips
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        val views = listOf("Overview", "Front", "Back", "Left", "Right")
                        items(views) { v ->
                            val isSelected = selectedViewNameState == v
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedViewNameState = v
                                    scale = 1f
                                    offsetX = 0f
                                    offsetY = 0f
                                },
                                label = { Text(v, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = BurgundyAccent,
                                    selectedLabelColor = WarmIvoryText,
                                    containerColor = DeepNavySurface,
                                    labelColor = WarmIvoryText
                                )
                            )
                        }
                    }

                    // Toggle Markers & Reset View
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        OutlinedButton(
                            onClick = { showMarkersState = !showMarkersState },
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = if (showMarkersState) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = CopperAccent,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                scale = 1f
                                offsetX = 0f
                                offsetY = 0f
                            },
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = CopperAccent, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }

            // Attached Body Illustration Canvas Panel (Cropping per view, Zoom, Pan, Reset) on Ivory Paper Surface
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.3f)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.5.dp, CopperAccent, RoundedCornerShape(12.dp))
                        .background(ReadingPaperSurface)
                        .pointerInput(selectedViewNameState) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = (scale * zoom).coerceIn(1f, 3.5f)
                                offsetX += pan.x
                                offsetY += pan.y
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ReadingPaperSurface)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (imageBitmap != null) {
                            Canvas(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale,
                                        translationX = offsetX,
                                        translationY = offsetY
                                    )
                            ) {
                                val imgW = imageBitmap.width.toFloat()
                                val imgH = imageBitmap.height.toFloat()

                                val (srcX, srcW) = when (selectedViewNameState) {
                                    "Left" -> Pair(0f, imgW * 0.25f)
                                    "Front" -> Pair(imgW * 0.25f, imgW * 0.25f)
                                    "Back" -> Pair(imgW * 0.50f, imgW * 0.25f)
                                    "Right" -> Pair(imgW * 0.75f, imgW * 0.25f)
                                    else -> Pair(0f, imgW) // Overview
                                }

                                drawImage(
                                    image = imageBitmap,
                                    srcOffset = androidx.compose.ui.unit.IntOffset(srcX.toInt(), 0),
                                    srcSize = androidx.compose.ui.unit.IntSize(srcW.toInt(), imgH.toInt()),
                                    dstOffset = androidx.compose.ui.unit.IntOffset.Zero,
                                    dstSize = androidx.compose.ui.unit.IntSize(size.width.toInt(), size.height.toInt())
                                )
                            }
                        } else {
                            Box(modifier = Modifier.fillMaxSize().background(ReadingPaperSurface), contentAlignment = Alignment.Center) {
                                Text("Body Examination Reference Asset (body_examination_reference.png)", color = ReadingInk, fontSize = 12.sp)
                            }
                        }

                        // Tappable Examination Markers Overlay
                        if (showMarkersState) {
                            viewFilteredMarkers.forEachIndexed { idx, marker ->
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(
                                            start = (marker.xRatio * 320 - 160).dp,
                                            top = (marker.yRatio * 320 - 160).dp
                                        )
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(BurgundyAccent)
                                        .border(2.dp, AmberHighlight, CircleShape)
                                        .clickable { selectedMarker = marker },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${idx + 1}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                                        color = WarmIvoryText
                                    )
                                }
                            }
                        }

                        // View Label & Instructions Overlay
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(10.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(MidnightBackground.copy(alpha = 0.85f))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "View: $selectedViewNameState (Pinch to zoom / Pan)",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = WarmIvoryText
                            )
                        }
                    }
                }
            }

            // Accessible Text-List Equivalent Toggle
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Examination Findings (${markers.size} markers)",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                        color = WarmIvoryText
                    )

                    OutlinedButton(
                        onClick = { showAccessibleList = !showAccessibleList },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (showAccessibleList) "Hide Text List" else "Show Accessible Text List", fontSize = 11.sp)
                    }
                }
            }

            // Accessible List Alternative
            if (showAccessibleList || markers.isEmpty()) {
                items(markers) { marker ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, CopperAccent, RoundedCornerShape(10.dp))
                            .clickable { selectedMarker = marker },
                        colors = CardDefaults.cardColors(containerColor = ReadingPaperSurface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "[${marker.viewName}] ${marker.regionLabel}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = BurgundyAccent)
                                Text(text = "ID: ${marker.id}", fontSize = 10.sp, color = MutedText)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Observation: ${marker.observation}", fontSize = 12.sp, color = ReadingInk)
                        }
                    }
                }
            }

            item { DisclaimerBanner() }
        }
    }

    // Examination Marker Detail Modal Sheet with "Ask AI about this finding"
    selectedMarker?.let { marker ->
        ModalBottomSheet(
            onDismissRequest = { selectedMarker = null },
            sheetState = sheetState,
            containerColor = ReadingPaperSurface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Science, contentDescription = null, tint = BurgundyAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = marker.regionLabel,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                        color = ReadingInk
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "What was observed?", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = BurgundyAccent)
                Text(text = marker.observation, style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp), color = ReadingInk)

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "What could this mean?", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = CopperAccent)
                Text(text = marker.interpretation, style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp), color = ReadingInk)

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "What remains uncertain?", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = MutedText)
                Text(text = marker.uncertainty, style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp), color = ReadingInk)

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Key Takeaway:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = AmberHighlight)
                Text(text = marker.keyTakeaway, style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, fontWeight = FontWeight.Medium), color = ReadingInk)

                marker.relatedEvidenceId?.let { refId ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Related Discovered Evidence ID: $refId", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp), color = AmberHighlight)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // "Ask AI about this finding" action button
                Button(
                    onClick = {
                        val prompt = "Explain the medical and forensic interpretation of finding [${marker.regionLabel}]: ${marker.observation}"
                        selectedMarker = null
                        onOpenAskAiWithPrompt(prompt)
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavySurface, contentColor = WarmIvoryText),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = AmberHighlight)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Ask AI about this finding", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}
