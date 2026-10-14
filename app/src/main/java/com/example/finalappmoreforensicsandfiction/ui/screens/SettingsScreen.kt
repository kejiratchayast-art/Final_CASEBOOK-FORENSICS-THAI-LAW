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
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.data.AppMode
import com.example.finalappmoreforensicsandfiction.data.FontSizeOption
import com.example.finalappmoreforensicsandfiction.data.UserProgressState
import com.example.finalappmoreforensicsandfiction.ui.components.DisclaimerBanner
import com.example.finalappmoreforensicsandfiction.ui.components.ModeSwitchChip
import com.example.finalappmoreforensicsandfiction.ui.theme.CardBorderGold
import com.example.finalappmoreforensicsandfiction.ui.theme.DeepNavy
import com.example.finalappmoreforensicsandfiction.ui.theme.IncorrectRed
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedBurgundy
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.TextPrimary
import com.example.finalappmoreforensicsandfiction.ui.theme.TextSecondary

@Composable
fun SettingsScreen(
    userState: UserProgressState,
    onFontSizeSelect: (FontSizeOption) -> Unit,
    onSoundToggle: (Boolean) -> Unit,
    onModeChanged: (AppMode) -> Unit,
    onResetNotes: () -> Unit,
    onResetProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var showResetDialogState by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = ParchmentSurface,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Settings & Application Info",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = ParchmentSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Font Size Setting Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.FormatSize, contentDescription = null, tint = DeepNavy)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Narrative Text Size Scaling:",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = DeepNavy
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FontSizeOption.entries.forEach { option ->
                        val isSelected = userState.fontSizeOption == option
                        Button(
                            onClick = { onFontSizeSelect(option) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) DeepNavy else ParchmentSurface,
                                contentColor = if (isSelected) ParchmentSurface else TextPrimary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = option.label, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Sound Toggle Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (userState.isSoundEnabled) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeOff,
                        contentDescription = null,
                        tint = DeepNavy
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Audio & Sound Feedback",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                            color = DeepNavy
                        )
                        Text(
                            text = if (userState.isSoundEnabled) "Sound Enabled" else "Sound Disabled",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = TextPrimary.copy(alpha = 0.72f)
                        )
                    }
                }

                Switch(
                    checked = userState.isSoundEnabled,
                    onCheckedChange = onSoundToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = ParchmentSurface,
                        checkedTrackColor = DeepNavy
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // App Mode Switch Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Default Experience Mode:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                    color = DeepNavy
                )
                Spacer(modifier = Modifier.height(8.dp))
                ModeSwitchChip(
                    currentMode = userState.activeMode,
                    onModeChanged = onModeChanged,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Project & Educational Notice Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CardBorderGold, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = ParchmentSurface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = MutedBurgundy)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "About Project & Legal Sources",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = MutedBurgundy
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "'Casebook: Forensics & Thai Law' is a university mobile application final project.\n\n" +
                            "Fictional investigations and educational examination material support learning about forensic reasoning and Thai law. Legal translations are unofficial. Content marked Pending official-source verification must be checked against authoritative Thai documents before relying on it.",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 18.sp),
                    color = TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        DisclaimerBanner()

        Spacer(modifier = Modifier.height(20.dp))

        // Reset Options
        OutlinedButton(
            onClick = { onResetNotes() },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ParchmentSurface),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = ParchmentSurface)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Clear Personal Notes Only", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { showResetDialogState = true },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Reset All Progress & Saved Data", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        // Reset Confirmation Dialog
        if (showResetDialogState) {
            AlertDialog(
                onDismissRequest = { showResetDialogState = false },
                title = { Text("Reset All Application Data?", fontWeight = FontWeight.Bold) },
                text = { Text("This will permanently delete reading steps, collected evidence, activity answers, revealed resolutions, and personal notes. Proceed?", fontSize = 13.sp) },
                confirmButton = {
                    Button(
                        onClick = {
                            onResetProgress()
                            showResetDialogState = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = IncorrectRed, contentColor = ParchmentSurface)
                    ) {
                        Text("Reset Everything", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showResetDialogState = false }) {
                        Text("Cancel")
                    }
                },
                containerColor = ParchmentSurface,
                titleContentColor = TextPrimary,
                textContentColor = TextPrimary
            )
        }
    }
}
