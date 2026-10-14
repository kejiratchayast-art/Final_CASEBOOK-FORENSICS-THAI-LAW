package com.example.finalappmoreforensicsandfiction.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.LegalSource
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderGold
import com.example.finalappmoreforensicsandfiction.ui.theme.CorrectGreen
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary

@Composable
fun LegalSourceCard(
    legalSource: LegalSource,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isVerificationExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with Gavel Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Gavel,
                    contentDescription = null,
                    tint = MutedBurgundy,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = legalSource.lawTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = InkBlack
                    )
                    Text(
                        text = legalSource.sectionArticle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        ),
                        color = MutedBurgundy
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CorrectGreen.copy(alpha = 0.12f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = CorrectGreen,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = "Verification Pending",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = CorrectGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Section 1: Statutory Text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(InkBlack.copy(alpha = 0.04f))
                    .border(1.dp, CardBorderGold, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "1. Statutory Wording (Unofficial English Translation)",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    ),
                    color = InkBlack
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = legalSource.statutoryText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    ),
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Section 2: Educational Explanation
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(RestrainedGold.copy(alpha = 0.08f))
                    .border(1.dp, CardBorderGold, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "2. Plain-Language Educational Explanation",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    ),
                    color = MutedBurgundy
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = legalSource.educationalExplanation,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    ),
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Section 3: Case Application
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MutedBurgundy.copy(alpha = 0.06f))
                    .border(1.dp, CardBorderGold, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "3. Application to Fictional Case Facts",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    ),
                    color = MutedBurgundy
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = legalSource.caseApplication,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    ),
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Expandable Verification Details Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(InkBlack)
                    .clickable { isVerificationExpanded = !isVerificationExpanded }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = RestrainedGold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Source Verification & Legal Integrity Metadata",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        ),
                        color = ParchmentSurface
                    )
                }
                Icon(
                    imageVector = if (isVerificationExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = RestrainedGold
                )
            }

            // Expandable Content
            AnimatedVisibility(visible = isVerificationExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ParchmentSurface)
                        .border(1.dp, CardBorderGold, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    MetadataItem("Official Primary Source:", legalSource.officialSourceTitle)
                    MetadataItem("Relevant Amendment:", legalSource.relevantAmendment)
                    MetadataItem("Effective Date:", legalSource.effectiveDate)
                    MetadataItem("Last Content Verification Date:", legalSource.verificationDate)
                    MetadataItem("Verification Status:", legalSource.verificationStatus)
                    MetadataItem("Revision History & Version:", "${legalSource.revisionHistory} (${legalSource.contentVersion})")

                    Spacer(modifier = Modifier.height(10.dp))

                    // Readable Source Title & Full-Width Action Buttons
                    Text(
                        text = "Official source — Thai (${legalSource.officialSourceTitle})",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                        color = MutedBurgundy,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    OutlinedButton(
                        onClick = {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(legalSource.officialSourceUrl))
                                context.startActivity(intent)
                            } catch (_: Exception) {
                                // Fallback / handling missing browser apps or cert warnings
                                try {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Official Legal Source URL", legalSource.officialSourceUrl)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(
                                        context,
                                        "Official source currently unavailable (Certificate/Browser issue). Link copied to clipboard.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } catch (_: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Official source currently unavailable.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = DeepOxblood)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Launch,
                            contentDescription = null,
                            tint = DeepOxblood,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Text(
                            text = "Open Official Source",
                            color = DeepOxblood,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedButton(
                        onClick = {
                            try {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Official Legal Source URL", legalSource.officialSourceUrl)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(
                                    context,
                                    "Official source URL copied to clipboard.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (_: Exception) {
                                Toast.makeText(
                                    context,
                                    "Unable to copy link.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = null,
                            tint = InkBlack,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Text(
                            text = "Copy Link",
                            color = InkBlack,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MetadataItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 3.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            color = MutedBurgundy
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = TextPrimary
        )
    }
}
