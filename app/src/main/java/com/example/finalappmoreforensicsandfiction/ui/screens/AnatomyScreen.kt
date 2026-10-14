package com.example.finalappmoreforensicsandfiction.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.R
import com.example.finalappmoreforensicsandfiction.data.CasesProvider
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.HeaderBar
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderBrass
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepOxblood
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary

@Composable
fun AnatomyScreen(
    onOpenCase: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRegionState by remember { mutableStateOf("All") }

    val lessons = CasesProvider.allAnatomyLessons.filter {
        selectedRegionState == "All" || it.regionCategory == selectedRegionState
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderBar(title = "Anatomical Atlas — Body Structure & Trauma")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                                painter = painterResource(id = R.drawable.ic_section_anatomy),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.height(28.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ANATOMICAL ATLAS",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                                color = ParchmentSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Educational anatomical atlas teaching human body structures, tissue layers, and trauma biomechanics needed to understand medical evidence.",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp),
                            color = RestrainedGold
                        )
                    }
                }
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val regions = listOf("All", "Head & Neck", "Musculoskeletal & Trauma")
                    items(regions) { region ->
                        val isSelected = selectedRegionState == region
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedRegionState = region },
                            label = { Text(region, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = InkBlack,
                                selectedLabelColor = ParchmentSurface,
                                containerColor = ParchmentSurface,
                                labelColor = TextPrimary
                            )
                        )
                    }
                }
            }

            item { DisclaimerBanner() }

            items(lessons) { lesson ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, CardBorderBrass, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = lesson.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                                color = InkBlack,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = lesson.regionCategory,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = ParchmentSurface,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(DeepOxblood)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = lesson.summary, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp), color = TextPrimary)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = lesson.structuralDescription, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp), color = TextSecondary)

                        Spacer(modifier = Modifier.height(10.dp))

                        // Anatomical Terms Badges
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Key Terms: ", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold), color = MutedBurgundy)
                            lesson.anatomicalTerms.forEach { term ->
                                Text(
                                    text = term,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = ParchmentSurface,
                                    modifier = Modifier
                                        .padding(horizontal = 2.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(InkBlack)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = lesson.relevantCaseContext, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium), color = DeepOxblood)

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { onOpenCase("case_01") },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = InkBlack, contentColor = ParchmentSurface),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Explore Associated Case", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = RestrainedGold)
                        }
                    }
                }
            }
        }
    }
}
