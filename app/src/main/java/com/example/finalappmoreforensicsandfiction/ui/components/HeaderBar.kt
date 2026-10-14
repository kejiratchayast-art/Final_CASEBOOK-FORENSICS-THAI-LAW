package com.example.finalappmoreforensicsandfiction.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalappmoreforensicsandfiction.R
import com.example.finalappmoreforensicsandfiction.data.AppMode
import com.example.finalappmoreforensicsandfiction.ui.theme.InkBlack
import com.example.finalappmoreforensicsandfiction.ui.theme.ParchmentSurface
import com.example.finalappmoreforensicsandfiction.ui.theme.RestrainedGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    isBookmarked: Boolean? = null,
    onBookmarkToggle: (() -> Unit)? = null,
    activeMode: AppMode? = null,
    onModeChanged: ((AppMode) -> Unit)? = null
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onBackClick == null) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_detective_emblem),
                        contentDescription = "Casebook Emblem",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    ),
                    color = ParchmentSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (activeMode != null && onModeChanged != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    ModeSwitchChip(
                        currentMode = activeMode,
                        onModeChanged = onModeChanged,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = RestrainedGold
                    )
                }
            }
        },
        actions = {
            if (isBookmarked != null && onBookmarkToggle != null) {
                IconButton(onClick = onBookmarkToggle) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) RestrainedGold else ParchmentSurface.copy(alpha = 0.7f)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = InkBlack,
            titleContentColor = ParchmentSurface,
            navigationIconContentColor = RestrainedGold,
            actionIconContentColor = RestrainedGold
        )
    )
}
