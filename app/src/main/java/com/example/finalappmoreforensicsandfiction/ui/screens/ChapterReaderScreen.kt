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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.AppMode
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderGold
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepNavy
import com.example.finalappmoreforensicsandfiction.ui.theme.GoldLight
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary

@Composable
fun ChapterReaderScreen(
    caseData: CaseData,
    userState: UserProgressState,
    onBackClick: () -> Unit,
    onNavigateToScene: () -> Unit,
    onNavigateToActivities: () -> Unit,
    onFontSizeCycle: () -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val textScale = userState.fontSizeOption.scale
    val isBookmarked = userState.bookmarkedCaseIds.contains(caseData.id)

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HeaderBar(
            title = "Chapter 1: ${caseData.title}",
            onBackClick = onBackClick,
            isBookmarked = isBookmarked,
            onBookmarkToggle = onBookmarkToggle
        )

        // Reading Controls Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeepNavy.copy(alpha = 0.95f))
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null,
                    tint = RestrainedGold,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    text = "Mode: ${userState.activeMode.title}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = ParchmentSurface
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Text Size: ${userState.fontSizeOption.label}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = GoldLight
                )
                IconButton(onClick = onFontSizeCycle) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = "Adjust Font Size",
                        tint = RestrainedGold
                    )
                }
            }
        }

        // Chapter Progress Bar
        LinearProgressIndicator(
            progress = { 0.25f },
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
            // Chapter Heading
            Text(
                text = "CHAPTER 1 NARRATIVE",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = (12 * textScale).sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MutedBurgundy
            )

            Text(
                text = caseData.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = (22 * textScale).sp,
                    fontWeight = FontWeight.Bold
                ),
                color = DeepNavy
            )

            Text(
                text = caseData.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = (13 * textScale).sp,
                    fontWeight = FontWeight.Medium
                ),
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Short formatted paragraphs
            val paragraphs = caseData.introductionText.split("\n\n")
            paragraphs.forEach { para ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(1.dp, CardBorderGold, RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Text(
                        text = para,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = (14 * textScale).sp,
                            lineHeight = (22 * textScale).sp
                        ),
                        color = TextPrimary,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Knowledge Highlights
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = RestrainedGold.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = MutedBurgundy
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Key Forensic Concepts Introduced:",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = (13 * textScale).sp
                            ),
                            color = MutedBurgundy
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    caseData.forensicConcepts.forEach { concept ->
                        Text(
                            text = "• ${concept.title}: ${concept.summary}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = (12 * textScale).sp,
                                lineHeight = (17 * textScale).sp
                            ),
                            color = TextPrimary,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DisclaimerBanner()

            Spacer(modifier = Modifier.height(20.dp))

            // Navigation Actions based on Mode
            if (userState.activeMode == AppMode.INVESTIGATE) {
                Button(
                    onClick = onNavigateToScene,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepNavy,
                        contentColor = ParchmentSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = RestrainedGold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Enter 2D Investigation Scene",
                        fontWeight = FontWeight.Bold,
                        fontSize = (14 * textScale).sp
                    )
                }
            } else {
                Button(
                    onClick = onNavigateToScene,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepNavy,
                        contentColor = ParchmentSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = null,
                        tint = RestrainedGold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Inspect Physical Evidence & Scene",
                        fontWeight = FontWeight.Bold,
                        fontSize = (14 * textScale).sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onNavigateToActivities,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Proceed directly to Activities",
                        color = DeepNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = (13 * textScale).sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = DeepNavy
                    )
                }
            }
        }
    }
}
