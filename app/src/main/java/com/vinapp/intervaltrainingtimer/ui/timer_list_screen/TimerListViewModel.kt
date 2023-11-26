package com.vinapp.intervaltrainingtimer.ui.timer_list_screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.vinapp.intervaltrainingtimer.App
import com.vinapp.intervaltrainingtimer.base.presentation.BaseViewModel
import com.vinapp.intervaltrainingtimer.data.timer.TimerRepository
import com.vinapp.intervaltrainingtimer.mapping.TimerMapper.mapTimerToTimerItemData
import com.vinapp.intervaltrainingtimer.ui.timer_list_screen.TimerListScreenAction.NavigateToTimerEditorScreen
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
        currentScreenState.timerList?.getOrNull(index)?.id?.let { timerId ->
            sendAction(
                NavigateToTimerEditorScreen(
                    timerId = timerId
                )
            )
        }
    }

    fun onAddTimerClick() {
        sendAction(
            NavigateToTimerEditorScreen(
                timerId = null
            )
        )
    }
}