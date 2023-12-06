package com.vinapp.intervaltrainingtimer.ui.timer_list_screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.vinapp.intervaltrainingtimer.App
import com.vinapp.intervaltrainingtimer.base.presentation.BaseViewModel
import com.vinapp.intervaltrainingtimer.data.source.timer.TimerRepository
import com.vinapp.intervaltrainingtimer.mapping.TimerMapper.mapTimerToTimerItemData
import com.vinapp.intervaltrainingtimer.ui.timer_list_screen.TimerListScreenAction.NavigateToTimerEditorScreen
import com.vinapp.intervaltrainingtimer.ui.timer_list_screen.TimerListScreenAction.NavigateToTimerScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TimerListViewModel(
    private val timerRepository: TimerRepository
) : BaseViewModel<TimerListScreenState, TimerListScreenAction>() {

    override val mutableScreenStateFlow = MutableStateFlow(TimerListScreenState())
    override val mutableScreenActionChannel = Channel<TimerListScreenAction>()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as App
                TimerListViewModel(
                    timerRepository = app.container.timerRepository
                )
            }
        }
    }

    fun loadTimerList() {
        timerRepository.getTimerListFlow()
            .distinctUntilChanged()
            .onEach {
                updateState(
                    currentScreenState.copy(
                        timerList = it.map(::mapTimerToTimerItemData)
                    )
                )
            }
            .launchIn(CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO))
    }

    fun onTimerClick(index: Int) {
        val selectedItem = if (index != currentScreenState.selectedItem) index else null
        updateStateOnSelectedItemChanged(selectedItem)
    }

    fun onAddTimerClick() {
        updateStateOnSelectedItemChanged(null)
        sendAction(
            NavigateToTimerEditorScreen(
                timerId = null
            )
        )
    }

    fun onDeleteTimerClick() {
        currentScreenState.selectedItem?.let { selectedItem ->
            currentScreenState.timerList?.getOrNull(selectedItem)?.id?.let { timerId ->
                updateStateOnSelectedItemChanged(null)
                viewModelScope.launch(Dispatchers.IO) {
                    timerRepository.deleteTimer(
                        timerId = timerId
                    )
                }
            }
        }
    }

    fun onStartTimerClick() {
        currentScreenState.selectedItem?.let { selectedItem ->
            currentScreenState.timerList?.getOrNull(selectedItem)?.id?.let { timerId ->
                updateStateOnSelectedItemChanged(null)
                sendAction(
                    NavigateToTimerScreen(
                        timerId = timerId
                    )
                )
            }
        }
    }

    fun onEditTimerClick() {
        currentScreenState.selectedItem?.let { selectedItem ->
            currentScreenState.timerList?.getOrNull(selectedItem)?.id?.let { timerId ->
                updateStateOnSelectedItemChanged(null)
                sendAction(
                    NavigateToTimerEditorScreen(
                        timerId = timerId
                    )
                )
            }
        }
    }

    private fun updateStateOnSelectedItemChanged(selectedItem: Int?) {
        updateState(
            currentScreenState.copy(
                selectedItem = selectedItem,
                showDeleteButton = selectedItem != null,
                showStartButton = selectedItem != null,
                showEditButton = selectedItem != null,
            )
        )
    }
}