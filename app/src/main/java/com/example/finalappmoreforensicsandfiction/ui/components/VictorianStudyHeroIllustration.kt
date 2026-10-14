package com.example.finalappmoreforensicsandfiction.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.BrassLight
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.DarkWalnut
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.ForestGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.WarmParchment

@Composable
fun VictorianStudyHeroIllustration(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(2.dp, CardBorderBrass, RoundedCornerShape(14.dp))
            .background(InkBlack)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height

            // 1. Background Wall & Bookcase Shadow
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F172A), Color(0xFF1E1510), DarkWalnut)
                ),
                size = Size(w, h * 0.68f)
            )

            // Bookcase Shelves Silhouette in Background (Left Side)
            drawRect(color = ForestGreen.copy(alpha = 0.25f), topLeft = Offset(w * 0.05f, h * 0.1f), size = Size(w * 0.25f, h * 0.5f))
            for (b in 0..3) {
                drawRect(
                    color = Color(0xFF38231A),
                    topLeft = Offset(w * 0.06f + b * (w * 0.05f), h * 0.15f),
                    size = Size(w * 0.04f, h * 0.40f)
                )
            }

            // 2. Rain-Streaked Arched Window (Right Center)
            val windowWidth = w * 0.28f
            val windowHeight = h * 0.48f
            val windowLeft = w * 0.38f
            val windowTop = h * 0.08f

            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF38BDF8).copy(alpha = 0.30f), Color(0xFF1E293B).copy(alpha = 0.8f))
                ),
                topLeft = Offset(windowLeft, windowTop),
                size = Size(windowWidth, windowHeight),
                cornerRadius = CornerRadius(18f, 18f)
            )
            drawRoundRect(
                color = AntiqueBrass,
                topLeft = Offset(windowLeft, windowTop),
                size = Size(windowWidth, windowHeight),
                cornerRadius = CornerRadius(18f, 18f),
                style = Stroke(width = 2.dp.toPx())
            )

            // Rain streaks on glass
            for (r in 0..5) {
                drawLine(
                    color = Color(0xFFE0F2FE).copy(alpha = 0.35f),
                    start = Offset(windowLeft + 10f + r * 15f, windowTop + 10f),
                    end = Offset(windowLeft + 5f + r * 15f, windowTop + windowHeight - 10f),
                    strokeWidth = 1.5f
                )
            }

            // 3. Dark Walnut Desk Surface
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF38231A), Color(0xFF21140E), Color(0xFF140C08))
                ),
                topLeft = Offset(0f, h * 0.65f),
                size = Size(w, h * 0.35f)
            )
            drawLine(
                color = AntiqueBrass.copy(alpha = 0.6f),
                start = Offset(0f, h * 0.65f),
                end = Offset(w, h * 0.65f),
                strokeWidth = 3.dp.toPx()
            )

            // 4. Brass Desk Lamp & Volumetric Warm Light Cone
            val lampX = w * 0.82f
            val lampY = h * 0.22f

            val lightPath = Path().apply {
                moveTo(lampX, lampY + 15f)
                lineTo(w * 0.35f, h)
                lineTo(w, h)
                close()
            }
            drawPath(
                path = lightPath,
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFDE047).copy(alpha = 0.50f), Color(0xFFCA8A04).copy(alpha = 0.18f), Color.Transparent),
                    center = Offset(lampX, lampY),
                    radius = w * 0.65f
                )
            )

            // Brass Lamp Stand
            drawLine(
                color = AntiqueBrass,
                start = Offset(lampX, lampY),
                end = Offset(lampX, h * 0.68f),
                strokeWidth = 6.dp.toPx()
            )
            drawOval(color = AntiqueBrass, topLeft = Offset(lampX - 20f, h * 0.66f), size = Size(40f, 16f))

            // Green Banker Shade
            val shadePath = Path().apply {
                moveTo(lampX - 30f, lampY + 15f)
                lineTo(lampX + 30f, lampY + 15f)
                lineTo(lampX + 22f, lampY - 10f)
                lineTo(lampX - 22f, lampY - 10f)
                close()
            }
            drawPath(path = shadePath, color = ForestGreen)
            drawPath(path = shadePath, color = AntiqueBrass, style = Stroke(width = 2.dp.toPx()))

            // 5. Oxblood Leather Dossier & Scattered Papers
            val dossierLeft = w * 0.48f
            val dossierTop = h * 0.68f
            val dossierWidth = w * 0.28f
            val dossierHeight = h * 0.22f

            // Scattered Paper behind dossier
            drawRect(
                color = WarmParchment,
                topLeft = Offset(dossierLeft - 15f, dossierTop + 8f),
                size = Size(dossierWidth, dossierHeight * 0.8f)
            )

            // Oxblood Dossier
            drawRoundRect(
                color = DeepOxblood,
                topLeft = Offset(dossierLeft, dossierTop),
                size = Size(dossierWidth, dossierHeight),
                cornerRadius = CornerRadius(6f)
            )
            drawRoundRect(
                color = AntiqueBrass,
                topLeft = Offset(dossierLeft, dossierTop),
                size = Size(dossierWidth, dossierHeight),
                cornerRadius = CornerRadius(6f),
                style = Stroke(width = 2.dp.toPx())
            )
            // Red Wax Seal on Dossier
            drawCircle(
                color = Color(0xFF991B1B),
                radius = 10f,
                center = Offset(dossierLeft + dossierWidth * 0.8f, dossierTop + dossierHeight * 0.5f)
            )

            // 6. Magnifying Glass
            val glassX = w * 0.36f
            val glassY = h * 0.78f
            drawCircle(
                color = Color(0xFF38BDF8).copy(alpha = 0.25f),
                radius = 18f,
                center = Offset(glassX, glassY)
            )
            drawCircle(
                color = AntiqueBrass,
                radius = 18f,
                center = Offset(glassX, glassY),
                style = Stroke(width = 3.dp.toPx())
            )
            drawLine(
                color = AntiqueBrass,
                start = Offset(glassX + 12f, glassY + 12f),
                end = Offset(glassX + 32f, glassY + 32f),
                strokeWidth = 5.dp.toPx()
            )
        }

        // Overlay Title Branding Bar
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, DarkWalnut.copy(alpha = 0.85f), DarkWalnut)
                    )
                )
                .padding(14.dp)
        ) {
            Text(
                text = "CASEBOOK",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    letterSpacing = 2.sp
                ),
                color = WarmParchment
            )
            Text(
                text = "Forensics & Thai Law — Private Investigation Archive",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = BrassLight
            )
        }
    }
}
