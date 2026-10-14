package com.example.finalappmoreforensicsandfiction.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.ui.theme.MainBackground
import com.example.finalappmoreforensicsandfiction.ui.theme.MainText
import com.example.finalappmoreforensicsandfiction.ui.theme.MutedGold
import com.example.finalappmoreforensicsandfiction.ui.theme.WineRed

enum class MainTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    CASES("Cases", Icons.Default.FolderSpecial),
    ASK_AI("Ask AI", Icons.Default.AutoAwesome),
    LIBRARY("Library", Icons.Default.LocalLibrary),
    SETTINGS("Settings", Icons.Default.Settings)
}

@Composable
fun BottomNavBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    NavigationBar(
        containerColor = MainBackground,
        contentColor = MainText
    ) {
        MainTab.entries.forEach { tab ->
            val isSelected = selectedTab == tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label
                    )
                },
                label = {
                    Text(
                        text = tab.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MainText,
                    selectedTextColor = MutedGold,
                    indicatorColor = WineRed,
                    unselectedIconColor = MutedGold.copy(alpha = 0.6f),
                    unselectedTextColor = MainText.copy(alpha = 0.6f)
                )
            )
        }
    }
}
