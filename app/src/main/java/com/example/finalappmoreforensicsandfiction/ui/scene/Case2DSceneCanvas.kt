package com.example.finalappmoreforensicsandfiction.ui.scene

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.EvidenceHotspot
import com.example.finalappmoreforensicsandfiction.data.SceneType
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.BrassLight
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.HotspotPulse
import com.example.finalappmoreforensicsandfiction.ui.theme.MidnightNavy
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface

@Composable
fun Case2DSceneCanvas(
    sceneType: SceneType,
    hotspots: List<EvidenceHotspot>,
    collectedHotspotIds: Set<String>,
    selectedHotspot: EvidenceHotspot?,
    showLocationsHint: Boolean,
    onToggleHint: () -> Unit,
    onHotspotSelect: (EvidenceHotspot) -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by transition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        // Mode & Hint Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Default.TouchApp,
                    contentDescription = null,
                    tint = MutedBurgundy,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Tap scene hotspots to inspect physical evidence",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    color = MidnightNavy
                )
            }

            // Show Locations Hint Toggle
            AssistChip(
                onClick = onToggleHint,
                label = {
                    Text(
                        text = if (showLocationsHint) "Hide Hints" else "Hint Locations",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = if (showLocationsHint) HotspotPulse else MidnightNavy,
                        modifier = Modifier.size(14.dp)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (showLocationsHint) MidnightNavy else ParchmentSurface,
                    labelColor = if (showLocationsHint) ParchmentSurface else MidnightNavy
                )
            )
        }

        // 2D Scene Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, CardBorderBrass, RoundedCornerShape(12.dp))
                .background(MidnightNavy)
                .pointerInput(hotspots) {
                    detectTapGestures { offset ->
                        val width = size.width
                        val height = size.height
                        val tapped = hotspots.minByOrNull { hotspot ->
                            val hX = hotspot.xRatio * width
                            val hY = hotspot.yRatio * height
                            val dx = offset.x - hX
                            val dy = offset.y - hY
                            dx * dx + dy * dy
                        }
                        if (tapped != null) {
                            val hX = tapped.xRatio * width
                            val hY = tapped.yRatio * height
                            val distSq = (offset.x - hX) * (offset.x - hX) + (offset.y - hY) * (offset.y - hY)
                            if (distSq <= 120 * 120) {
                                onHotspotSelect(tapped)
                            }
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val w = size.width
                val h = size.height

                when (sceneType) {
                    SceneType.BALCONY_ROOM -> drawBalconyRoomScene(w, h)
                    SceneType.ART_GALLERY -> drawArtGalleryScene(w, h)
                    SceneType.HIGHWAY_CROSSING -> drawHighwayCrossingScene(w, h)
                }

                // Draw Hotspot markers
                hotspots.forEach { hotspot ->
                    val cx = hotspot.xRatio * w
                    val cy = hotspot.yRatio * h
                    val isCollected = collectedHotspotIds.contains(hotspot.id)
                    val isSelected = selectedHotspot?.id == hotspot.id

                    val activeRadius = if (showLocationsHint || isSelected) 20.dp.toPx() * pulseScale else 14.dp.toPx()

                    // Pulse outer ring
                    drawCircle(
                        color = if (showLocationsHint) HotspotPulse else if (isSelected) HotspotPulse else if (isCollected) CorrectGreen else MutedBurgundy,
                        radius = activeRadius,
                        center = Offset(cx, cy),
                        alpha = if (showLocationsHint) 0.5f else 0.35f
                    )

                    // Middle ring
                    drawCircle(
                        color = if (isCollected) CorrectGreen else AntiqueBrass,
                        radius = 12.dp.toPx(),
                        center = Offset(cx, cy),
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Center dot
                    drawCircle(
                        color = if (isCollected) CorrectGreen else if (isSelected || showLocationsHint) HotspotPulse else BrassLight,
                        radius = 7.dp.toPx(),
                        center = Offset(cx, cy)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Accessible Alternative Evidence List
        Text(
            text = "Evidence List (Accessibility Alternative):",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            color = MidnightNavy,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(hotspots) { hotspot ->
                val isCollected = collectedHotspotIds.contains(hotspot.id)
                val isSelected = selectedHotspot?.id == hotspot.id

                Card(
                    modifier = Modifier.clickable { onHotspotSelect(hotspot) },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MutedBurgundy else if (isCollected) MidnightNavy else ParchmentSurface
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = SolidColor(if (isSelected) BrassLight else CardBorderBrass)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (isCollected) CorrectGreen else AntiqueBrass)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = hotspot.title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            ),
                            color = if (isSelected || isCollected) ParchmentSurface else MidnightNavy
                        )
                        if (isCollected) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Saved",
                                tint = CorrectGreen,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// 2D Scene Drawers
private fun DrawScope.drawBalconyRoomScene(w: Float, h: Float) {
    drawRect(color = Color(0xFF1E293B), size = Size(w, h * 0.7f))
    drawRect(color = Color(0xFF452A18), topLeft = Offset(0f, h * 0.7f), size = Size(w, h * 0.3f))

    for (i in 0..6) {
        drawLine(
            color = Color(0xFF2E190E),
            start = Offset(0f, h * 0.7f + i * (h * 0.05f)),
            end = Offset(w, h * 0.7f + i * (h * 0.05f)),
            strokeWidth = 2f
        )
    }

    drawRect(
        color = Color(0xFF334155),
        topLeft = Offset(w * 0.2f, h * 0.15f),
        size = Size(w * 0.5f, h * 0.55f),
        style = Stroke(width = 6f)
    )
    drawRect(
        color = Color(0xFF38BDF8).copy(alpha = 0.15f),
        topLeft = Offset(w * 0.21f, h * 0.16f),
        size = Size(w * 0.48f, h * 0.53f)
    )

    for (x in 20..70 step 5) {
        drawLine(
            color = Color(0xFF64748B),
            start = Offset(w * (x / 100f), h * 0.5f),
            end = Offset(w * (x / 100f), h * 0.7f),
            strokeWidth = 4f
        )
    }
    drawLine(
        color = Color(0xFF94A3B8),
        start = Offset(w * 0.2f, h * 0.5f),
        end = Offset(w * 0.7f, h * 0.5f),
        strokeWidth = 8f
    )

    drawCircle(color = Color(0xFF881337), radius = 8f, center = Offset(w * 0.22f, h * 0.5f))

    drawRoundRect(
        color = Color(0xFF582F0E),
        topLeft = Offset(w * 0.65f, h * 0.58f),
        size = Size(w * 0.3f, h * 0.2f),
        cornerRadius = CornerRadius(8f)
    )
    drawCircle(color = Color(0xFFE2E8F0), radius = 10f, center = Offset(w * 0.75f, h * 0.64f))
    drawCircle(color = Color(0xFFCBD5E1), radius = 10f, center = Offset(w * 0.82f, h * 0.66f))

    drawOval(
        color = Color(0xFFF1F5F9).copy(alpha = 0.45f),
        topLeft = Offset(w * 0.28f, h * 0.70f),
        size = Size(w * 0.22f, h * 0.1f)
    )
}

private fun DrawScope.drawArtGalleryScene(w: Float, h: Float) {
    drawRect(color = Color(0xFF0F172A), size = Size(w, h * 0.75f))
    drawRect(color = Color(0xFF334155), topLeft = Offset(0f, h * 0.75f), size = Size(w, h * 0.25f))

    drawRect(
        color = Color(0xFFD4AF37),
        topLeft = Offset(w * 0.35f, h * 0.18f),
        size = Size(w * 0.3f, h * 0.38f),
        style = Stroke(width = 12f)
    )
    drawRect(
        color = Color(0xFF1E293B),
        topLeft = Offset(w * 0.37f, h * 0.20f),
        size = Size(w * 0.26f, h * 0.34f)
    )

    val cameraPath = Path().apply {
        moveTo(w * 0.78f, h * 0.1f)
        lineTo(w * 0.88f, h * 0.16f)
        lineTo(w * 0.85f, h * 0.22f)
        lineTo(w * 0.75f, h * 0.16f)
        close()
    }
    drawPath(path = cameraPath, color = Color(0xFF64748B))

    drawLine(
        color = Color(0xFFEF4444),
        start = Offset(w * 0.75f, h * 0.16f),
        end = Offset(w * 0.70f, h * 0.22f),
        strokeWidth = 4f
    )

    drawRect(color = Color(0xFF1E293B), topLeft = Offset(w * 0.08f, h * 0.3f), size = Size(w * 0.15f, h * 0.45f))
    drawRect(color = Color(0xFF94A3B8), topLeft = Offset(w * 0.08f, h * 0.3f), size = Size(w * 0.15f, h * 0.45f), style = Stroke(width = 4f))

    drawCircle(color = Color(0xFFDC2626), radius = 6f, center = Offset(w * 0.15f, h * 0.55f))

    drawRoundRect(
        color = Color(0xFF0284C7),
        topLeft = Offset(w * 0.82f, h * 0.42f),
        size = Size(w * 0.06f, h * 0.12f),
        cornerRadius = CornerRadius(4f)
    )

    for (i in 0..2) {
        drawOval(
            color = Color(0xFF78350F),
            topLeft = Offset(w * (0.2f + i * 0.08f), h * 0.8f),
            size = Size(20f, 32f)
        )
    }
}

private fun DrawScope.drawHighwayCrossingScene(w: Float, h: Float) {
    drawRect(color = Color(0xFF090D16), size = Size(w, h * 0.4f))
    drawRect(color = Color(0xFF1F2937), topLeft = Offset(0f, h * 0.4f), size = Size(w, h * 0.6f))

    for (i in 0..5) {
        drawRect(
            color = Color(0xFFF3F4F6).copy(alpha = 0.8f),
            topLeft = Offset(w * 0.35f + i * (w * 0.06f), h * 0.5f),
            size = Size(w * 0.035f, h * 0.3f)
        )
    }

    drawLine(color = Color(0xFF111827), start = Offset(w * 0.15f, h * 0.72f), end = Offset(w * 0.48f, h * 0.72f), strokeWidth = 10f)
    drawLine(color = Color(0xFF111827), start = Offset(w * 0.15f, h * 0.78f), end = Offset(w * 0.48f, h * 0.78f), strokeWidth = 10f)

    for (i in 0..6) {
        drawCircle(
            color = Color(0xFFE0F2FE).copy(alpha = 0.9f),
            radius = 5f,
            center = Offset(w * (0.2f + i * 0.02f), h * (0.68f + (i % 3) * 0.02f))
        )
    }

    drawCircle(color = Color(0xFF0284C7), radius = 8f, center = Offset(w * 0.65f, h * 0.60f))

    drawRect(color = Color(0xFF374151), topLeft = Offset(w * 0.48f, h * 0.1f), size = Size(w * 0.06f, h * 0.25f))
    drawCircle(color = Color(0xFFEF4444), radius = 10f, center = Offset(w * 0.51f, h * 0.16f))
    drawCircle(color = Color(0xFFEAB308), radius = 10f, center = Offset(w * 0.51f, h * 0.22f))
    drawCircle(color = Color(0xFF22C55E).copy(alpha = 0.3f), radius = 10f, center = Offset(w * 0.51f, h * 0.28f))

    for (i in 0..15) {
        drawLine(
            color = Color(0xFF38BDF8).copy(alpha = 0.3f),
            start = Offset(w * (i * 0.06f), h * 0.05f),
            end = Offset(w * (i * 0.06f - 0.02f), h * 0.95f),
            strokeWidth = 2f
        )
    }
}
