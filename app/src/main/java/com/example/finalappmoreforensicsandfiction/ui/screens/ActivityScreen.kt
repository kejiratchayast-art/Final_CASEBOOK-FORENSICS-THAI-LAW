package com.example.finalappmoreforensicsandfiction.ui.screens

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.ActivityOption
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderGold
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepNavy
import com.example.finalappmoreforensicsandfiction.ui.theme.IncorrectRed
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary
import com.example.finalappmoreforensicsandfiction.ui.theme.WarmIvoryText

@Composable
fun ActivityScreen(
    caseData: CaseData,
    userState: UserProgressState,
    onBackClick: () -> Unit,
    onSubmitAnswer: (activityId: String, optionId: Int, isCorrect: Boolean) -> Unit,
    onNavigateToLaw: () -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var currentActivityIndexState by remember { mutableIntStateOf(0) }
    val isBookmarked = userState.bookmarkedCaseIds.contains(caseData.id)

    val activities = caseData.activities
    val currentActivity = activities.getOrNull(currentActivityIndexState) ?: activities.first()
    val savedAnswerMap = userState.activityAnswersMap[caseData.id] ?: emptyMap()
    val selectedOptionId = savedAnswerMap[currentActivity.id]

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HeaderBar(
            title = "Activities: ${caseData.title}",
            onBackClick = onBackClick,
            isBookmarked = isBookmarked,
            onBookmarkToggle = onBookmarkToggle
        )

        LinearProgressIndicator(
            progress = { 0.60f },
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = MutedBurgundy,
            trackColor = CardBorderGold
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Activity Progress Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Activity ${currentActivityIndexState + 1}/${activities.size}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    color = ParchmentSurface,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MutedBurgundy)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )

                Text(
                    text = currentActivity.type.label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = RestrainedGold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Instruction Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = currentActivity.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = DeepNavy
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = currentActivity.instruction,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        ),
                        color = TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Options List
            currentActivity.options.forEach { option ->
                val isSelected = selectedOptionId == option.id

                ActivityOptionCard(
                    option = option,
                    isSelected = isSelected,
                    hasAnswered = selectedOptionId != null,
                    onSelect = {
                        onSubmitAnswer(currentActivity.id, option.id, option.isCorrect)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            DisclaimerBanner()

            Spacer(modifier = Modifier.height(18.dp))

            // Navigation Row between activities
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (currentActivityIndexState > 0) {
                    OutlinedButton(
                        onClick = { currentActivityIndexState-- },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ParchmentSurface),
                        border = BorderStroke(1.dp, CardBorderGold),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Previous Activity", fontWeight = FontWeight.Bold, color = ParchmentSurface)
                    }
                }

                if (currentActivityIndexState < activities.size - 1) {
                    Button(
                        onClick = { currentActivityIndexState++ },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MutedBurgundy, contentColor = ParchmentSurface),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Next Activity", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = RestrainedGold
                        )
                    }
                } else {
                    Button(
                        onClick = onNavigateToLaw,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MutedBurgundy, contentColor = ParchmentSurface),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Proceed to Thai Law Analysis", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = RestrainedGold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityOptionCard(
    option: ActivityOption,
    isSelected: Boolean,
    hasAnswered: Boolean,
    onSelect: () -> Unit
) {
    val borderColor = when {
        hasAnswered && isSelected && option.isCorrect -> CorrectGreen
        hasAnswered && isSelected && !option.isCorrect -> IncorrectRed
        isSelected -> DeepNavy
        else -> CardBorderGold
    }

    val containerColor = when {
        hasAnswered && isSelected && option.isCorrect -> CorrectGreen.copy(alpha = 0.08f).compositeOver(ParchmentSurface)
        hasAnswered && isSelected && !option.isCorrect -> IncorrectRed.copy(alpha = 0.08f).compositeOver(ParchmentSurface)
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
                        tint = DeepNavy,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Text(
                    text = option.text,
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
                        text = if (option.isCorrect) "Explanation (Correct):" else "Explanation (Incorrect or Unsupported):",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        ),
                        color = if (option.isCorrect) CorrectGreen else IncorrectRed
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = option.explanation,
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
}
