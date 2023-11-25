package com.vinapp.intervaltrainingtimer.ui_components.tab

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreenTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Tab(
        selected = isSelected,
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 16.dp
            ),
            text = title,
        )
    }
}