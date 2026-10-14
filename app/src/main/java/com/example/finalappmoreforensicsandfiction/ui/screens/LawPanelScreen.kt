package com.example.finalappmoreforensicsandfiction.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.CaseData
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.components.LegalSourceCard
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.MidnightNavy
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold

@Composable
fun LawPanelScreen(
    caseData: CaseData,
    userState: UserProgressState,
    onBackClick: () -> Unit,
    onNavigateToResolution: () -> Unit,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val isBookmarked = userState.bookmarkedCaseIds.contains(caseData.id)

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        HeaderBar(
            title = "Legal Brief: ${caseData.title}",
            onBackClick = onBackClick,
            isBookmarked = isBookmarked,
            onBookmarkToggle = onBookmarkToggle
        )

        LinearProgressIndicator(
            progress = { 0.80f },
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Gavel,
                    contentDescription = null,
                    tint = MutedBurgundy,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    text = "Legal Brief — Applicable Thai Statutory Provisions",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = MidnightNavy
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Legal Source Card / Legal Brief
            LegalSourceCard(legalSource = caseData.legalSource)

            Spacer(modifier = Modifier.height(16.dp))

            DisclaimerBanner()

            Spacer(modifier = Modifier.height(20.dp))

            // Continue to Resolution Button
            Button(
                onClick = onNavigateToResolution,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MidnightNavy,
                    contentColor = ParchmentSurface
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Proceed to Case Resolution & Reconstruction",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = RestrainedGold
                )
            }
        }
    }
}
