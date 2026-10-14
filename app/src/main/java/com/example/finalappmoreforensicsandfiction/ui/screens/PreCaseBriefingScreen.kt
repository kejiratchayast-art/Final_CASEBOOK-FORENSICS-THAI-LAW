package com.example.finalappmoreforensicsandfiction.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.finalappmoreforensicsandfiction.data.BriefingScenarioOption
import com.example.finalappmoreforensicsandfiction.data.PreCaseBriefingProvider
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.AntiqueBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.IncorrectRed
import com.example.finalappmoreforensicsandfiction.ui.theme.MidnightNavy
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary

@Composable
fun PreCaseBriefingScreen(
    onBackClick: () -> Unit,
    onProceedToInvestigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var currentScenarioIndexState by remember { mutableIntStateOf(0) }
    val scenarioAnswersMap = remember { mutableStateMapOf<String, Int>() }
    var isLegalPanelExpandedState by remember { mutableStateOf(false) }

    val scenarios = PreCaseBriefingProvider.briefingScenarios
    val currentScenario = scenarios.getOrNull(currentScenarioIndexState) ?: scenarios.first()
    val selectedOptionId = scenarioAnswersMap[currentScenario.id]

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HeaderBar(
            title = "Before You Investigate — Real-World Guidance",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Real-World vs Simulation Hero Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, CardBorderBrass, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MidnightNavy)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = RestrainedGold,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Real-World Civilian Guidance vs Game Simulation",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = ParchmentSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "In the real world, life safety and medical response always precede evidence preservation. Never enter unsafe scenes, touch potential evidence, or delay emergency assistance.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        ),
                        color = ParchmentSurface.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Emergency Numbers Bar in Thailand
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MutedBurgundy)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.LocalHospital, contentDescription = null, tint = ParchmentSurface)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Medical Emergency: 1669", color = ParchmentSurface, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.PhoneInTalk, contentDescription = null, tint = ParchmentSurface)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Police Emergency: 191", color = ParchmentSurface, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Simulation Boundary Notice
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderBrass, RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
            ) {
                Text(
                    text = PreCaseBriefingProvider.simulationBoundaryNotice,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                    color = TextPrimary,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Interactive Scenario Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Interactive Briefing (${currentScenarioIndexState + 1}/${scenarios.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    color = MidnightNavy
                )

                Text(
                    text = "Scenario",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = ParchmentSurface,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MutedBurgundy)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Scenario Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = currentScenario.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = MidnightNavy
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = currentScenario.situationText,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 18.sp),
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = currentScenario.questionText,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                        color = MutedBurgundy
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Options
            currentScenario.options.forEach { option ->
                val isSelected = selectedOptionId == option.id

                BriefingOptionCard(
                    option = option,
                    isSelected = isSelected,
                    hasAnswered = selectedOptionId != null,
                    onSelect = {
                        scenarioAnswersMap[currentScenario.id] = option.id
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Navigation Row between scenarios
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (currentScenarioIndexState > 0) {
                    OutlinedButton(
                        onClick = { currentScenarioIndexState-- },
                        modifier = Modifier.weight(1f).height(46.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Previous Scenario", color = MidnightNavy, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                if (currentScenarioIndexState < scenarios.size - 1) {
                    Button(
                        onClick = { currentScenarioIndexState++ },
                        modifier = Modifier.weight(1f).height(46.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MidnightNavy),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Next Scenario", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = RestrainedGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Expandable Legal Consequences Panel
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isLegalPanelExpandedState = !isLegalPanelExpandedState },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Gavel, contentDescription = null, tint = MutedBurgundy)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Possible Legal Consequences Panel",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                color = MidnightNavy
                            )
                        }
                        Icon(
                            imageVector = if (isLegalPanelExpandedState) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = MidnightNavy
                        )
                    }

                    AnimatedVisibility(visible = isLegalPanelExpandedState) {
                        val legal = PreCaseBriefingProvider.legalConsequencesPanel
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            Text(
                                text = legal.lawTitle,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                color = MidnightNavy
                            )
                            Text(
                                text = legal.sectionArticle,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                                color = MutedBurgundy
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = legal.statutoryText,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                                color = TextPrimary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = legal.educationalExplanation,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                                color = TextSecondary
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedButton(
                                onClick = {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(legal.officialSourceUrl))
                                        context.startActivity(intent)
                                    } catch (_: Exception) {
                                        Toast.makeText(context, "Cannot open external browser", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(imageVector = Icons.AutoMirrored.Filled.Launch, contentDescription = null, tint = MidnightNavy, modifier = Modifier.height(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Official source — Thai (${legal.officialSourceTitle})", color = MidnightNavy, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DisclaimerBanner()

            Spacer(modifier = Modifier.height(20.dp))

            // Proceed Button
            Button(
                onClick = onProceedToInvestigation,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MidnightNavy, contentColor = ParchmentSurface),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "I Understand — Proceed to Case Investigation",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RestrainedGold)
            }
        }
    }
}

@Composable
private fun BriefingOptionCard(
    option: BriefingScenarioOption,
    isSelected: Boolean,
    hasAnswered: Boolean,
    onSelect: () -> Unit
) {
    val borderColor = when {
        hasAnswered && isSelected && option.isCorrect -> CorrectGreen
        hasAnswered && isSelected && !option.isCorrect -> IncorrectRed
        isSelected -> MidnightNavy
        else -> CardBorderBrass
    }

    val containerColor = when {
        hasAnswered && isSelected && option.isCorrect -> CorrectGreen.copy(alpha = 0.08f)
        hasAnswered && isSelected && !option.isCorrect -> IncorrectRed.copy(alpha = 0.08f)
        else -> ParchmentSurface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable { onSelect() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (hasAnswered && isSelected) {
                    Icon(
                        imageVector = if (option.isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                        contentDescription = null,
                        tint = if (option.isCorrect) CorrectGreen else IncorrectRed,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Text(
                    text = option.optionText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
            }

            AnimatedVisibility(visible = hasAnswered && isSelected) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(ParchmentSurface)
                        .border(1.dp, borderColor, RoundedCornerShape(6.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = if (option.isCorrect) "Explanatory Feedback (Correct):" else "Explanatory Feedback (Incorrect):",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                        color = if (option.isCorrect) CorrectGreen else IncorrectRed
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = option.explanation,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                        color = TextPrimary
                    )
                }
            }
        }
    }
}
