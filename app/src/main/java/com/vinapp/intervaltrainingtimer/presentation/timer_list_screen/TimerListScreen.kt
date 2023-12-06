package com.vinapp.intervaltrainingtimer.presentation.timer_list_screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.ui_components.add_item.AddItem
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme
import com.vinapp.intervaltrainingtimer.ui_components.timer_item.TimerItem
import com.vinapp.intervaltrainingtimer.presentation.timer_list_screen.TimerListScreenAction.NavigateToTimerEditorScreen
import com.vinapp.intervaltrainingtimer.presentation.timer_list_screen.TimerListScreenAction.NavigateToTimerScreen
import com.vinapp.intervaltrainingtimer.ui_components.bottom_buttons_container.BottomButtonsContainer
import kotlinx.coroutines.launch

@Composable
fun TimerListScreen(
    navigateToTimerEditorScreen: (timerId: String?) -> Unit,
    navigateToTimerScreen: (timerId: String) -> Unit
) {
    val viewModel: TimerListViewModel = viewModel(
        factory = TimerListViewModel.Factory
    )
    val state by viewModel.screenStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            viewModel.screenActionFlow.collect { action ->
                when (action) {
                    is NavigateToTimerEditorScreen -> navigateToTimerEditorScreen(action.timerId)
                    is NavigateToTimerScreen -> navigateToTimerScreen(action.timerId)
                }
            }
        }
        viewModel.loadTimerList()
    }

    TimerListScreenContent(
        state = state,
        onTimerClick = viewModel::onTimerClick,
        onAddTimerClick = viewModel::onAddTimerClick,
        onDeleteClick = viewModel::onDeleteTimerClick,
        onStartClick = viewModel::onStartTimerClick,
        onEditClick = viewModel::onEditTimerClick
    )
}

@Composable
private fun TimerListScreenContent(
    state: TimerListScreenState,
    onTimerClick: (index: Int) -> Unit,
    onAddTimerClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onStartClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    BottomButtonsContainer(
        leftButtonText = stringResource(R.string.delete),
        rightButtonText = stringResource(R.string.edit),
        showLeftButton = state.showDeleteButton,
        showCenterButton = state.showStartButton,
        showRightButton = state.showEditButton,
        centerButtonIcon = R.drawable.ic_play,
        onLeftButtonClick = onDeleteClick,
        onCenterButtonClick = onStartClick,
        onRightButtonClick = onEditClick
    ) {
        Scaffold(
            backgroundColor = AppTheme.colors.lightGray
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(
                    start = 14.dp,
                    top = 8.dp,
                    end = 14.dp,
                    bottom = 128.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.timerList?.let { timers ->
                    itemsIndexed(timers) { index, timer ->
                        TimerItem(
                            timerItemData = timer,
                            isSelected = state.selectedItem == index,
                            onClick = {
                                onTimerClick(index)
                            }
                        )
                    }
                }
                item {
                    AddItem {
                        onAddTimerClick()
                    }
                }
            }
        }
    }
}