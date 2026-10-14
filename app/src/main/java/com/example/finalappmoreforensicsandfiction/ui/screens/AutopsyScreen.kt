package com.example.finalappmoreforensicsandfiction.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.R
import com.example.finalappmoreforensicsandfiction.data.AutopsyReport
import com.example.finalappmoreforensicsandfiction.data.CasesProvider
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.DarkWalnut
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary

@Composable
fun AutopsyScreen(
    onOpenCase: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderBar(title = "Examination Room — Autopsy Reports")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Hero Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = InkBlack)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_section_autopsy),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.height(28.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AUTOPSY EXAMINATION ROOM",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                ),
                                color = ParchmentSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Educational medical-atlas examination reports detailing post-mortem findings, injury patterns, and toxicological screening summaries.",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                            color = RestrainedGold
                        )
                    }
                }
            }

            item { DisclaimerBanner() }

            items(CasesProvider.allAutopsyReports) { report ->
                AutopsyReportCard(report = report, onOpenCase = onOpenCase)
            }
        }
    }
}

@Composable
fun AutopsyReportCard(
    report: AutopsyReport,
    onOpenCase: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "REPORT REF: ${report.id.uppercase()}",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                    color = ParchmentSurface,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(DeepOxblood)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )

                Text(
                    text = "${report.gender}, Age ${report.age}",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
                    color = DarkWalnut
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Subject: ${report.victimName}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                color = InkBlack
            )

            Text(
                text = report.examinerTitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Neutral Front/Back Medical Body Outline Canvas Diagram
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.2f)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, CardBorderBrass, RoundedCornerShape(8.dp))
                    .background(InkBlack)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height

                    // Drawing Front and Back Anatomical Outlines
                    val fX = w * 0.25f
                    val bX = w * 0.75f

                    // Front Outline
                    drawCircle(color = AntiqueBrass, radius = 12f, center = Offset(fX, h * 0.2f))
                    drawLine(color = AntiqueBrass, start = Offset(fX, h * 0.28f), end = Offset(fX, h * 0.75f), strokeWidth = 3f)
                    drawLine(color = AntiqueBrass, start = Offset(fX - 25f, h * 0.35f), end = Offset(fX + 25f, h * 0.35f), strokeWidth = 3f)
                    drawLine(color = AntiqueBrass, start = Offset(fX, h * 0.75f), end = Offset(fX - 15f, h * 0.95f), strokeWidth = 3f)
                    drawLine(color = AntiqueBrass, start = Offset(fX, h * 0.75f), end = Offset(fX + 15f, h * 0.95f), strokeWidth = 3f)

                    // Back Outline
                    drawCircle(color = AntiqueBrass, radius = 12f, center = Offset(bX, h * 0.2f))
                    drawLine(color = AntiqueBrass, start = Offset(bX, h * 0.28f), end = Offset(bX, h * 0.75f), strokeWidth = 3f)
                    drawLine(color = AntiqueBrass, start = Offset(bX - 25f, h * 0.35f), end = Offset(bX + 25f, h * 0.35f), strokeWidth = 3f)
                    drawLine(color = AntiqueBrass, start = Offset(bX, h * 0.75f), end = Offset(bX - 15f, h * 0.95f), strokeWidth = 3f)
                    drawLine(color = AntiqueBrass, start = Offset(bX, h * 0.75f), end = Offset(bX + 15f, h * 0.95f), strokeWidth = 3f)

                    // Numbered Injury Markers
                    drawCircle(color = Color(0xFFEF4444), radius = 8f, center = Offset(bX, h * 0.22f)) // Scalp marker
                    drawCircle(color = Color(0xFFEF4444), radius = 8f, center = Offset(fX + 10f, h * 0.85f)) // Leg marker
                }

                Text(
                    text = "NEUTRAL ANATOMICAL DIAGRAM (FRONT / BACK)",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = RestrainedGold,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // External Observations
            Text(
                text = "1. External Examination Observations:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                color = InkBlack
            )

            report.externalObservations.forEach { obs ->
                Text(text = "• $obs", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = TextPrimary, modifier = Modifier.padding(top = 2.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Internal Findings
            Text(
                text = "2. Internal Examination Findings:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                color = DeepOxblood
            )

            report.internalFindings.forEach { finding ->
                Text(text = "• $finding", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = TextPrimary, modifier = Modifier.padding(top = 2.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Toxicology Summary
            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, CardBorderBrass, RoundedCornerShape(8.dp)),
                colors = CardDefaults.cardColors(containerColor = RestrainedGold.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Science, contentDescription = null, tint = DeepOxblood)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Toxicological Screening Summary:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = DeepOxblood)
                    }
                    Text(text = report.toxicologySummary, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = TextPrimary)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Limitations
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MutedBurgundy.copy(alpha = 0.08f))
                    .padding(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = MutedBurgundy)
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(text = "Limitations & Uncertainties:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp), color = MutedBurgundy)
                    Text(text = report.limitationsAndUncertainties, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = TextPrimary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Cross-Link to Case Investigation
            Button(
                onClick = { onOpenCase(report.caseId) },
                modifier = Modifier.fillMaxWidth().height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = InkBlack, contentColor = ParchmentSurface),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Open Linked Case Investigation", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RestrainedGold)
            }
        }
    }
}
