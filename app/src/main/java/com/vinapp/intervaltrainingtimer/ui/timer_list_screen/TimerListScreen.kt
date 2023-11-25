package com.vinapp.intervaltrainingtimer.ui.timer_list_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinapp.intervaltrainingtimer.ui_components.add_item.AddItem
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme
import com.vinapp.intervaltrainingtimer.ui_components.timer_item.TimerItem
import com.vinapp.intervaltrainingtimer.ui.timer_list_screen.TimerListScreenAction.NavigateToTimerEditorScreen
import kotlinx.coroutines.launch

@Composable
fun TimerListScreen(
    navigateToTimerEditorScreen: (timerId: String?) -> Unit
) {
    val viewModel: TimerListViewModel = viewModel(
        factory = TimerListViewModel.Factory
    )
    val state by viewModel.screenStateFlow.collectAsState()

    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    LaunchedEffect(Unit) {
        lifecycleScope.launch {
            viewModel.screenActionFlow.collect { action ->
                when (action) {
                    is NavigateToTimerEditorScreen -> navigateToTimerEditorScreen(action.timerId)
                }
            }
        }
    }

    Scaffold(
        backgroundColor = AppTheme.colors.lightGray
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(
                horizontal = 14.dp,
                vertical = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.timerList?.let { timers ->
                itemsIndexed(timers) { index, timer ->
                    TimerItem(
                        timerItemData = timer,
                        onClick = {
                            viewModel.onTimerClick(index)
                        }
                    )
                }
            }
            item {
                AddItem {
                    viewModel.onAddTimerClick()
                }
            }
        }
    }
}