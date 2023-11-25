package com.vinapp.intervaltrainingtimer.base.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S: ScreenState, A: ScreenAction> : ViewModel() {

    protected abstract val mutableScreenStateFlow: MutableStateFlow<S>
    val screenStateFlow: StateFlow<S> get() = mutableScreenStateFlow
    protected val currentScreenState: S get() = screenStateFlow.value
    protected abstract val mutableScreenActionChannel: Channel<A>
    val screenActionFlow: Flow<A> get() = mutableScreenActionChannel.receiveAsFlow()

    protected fun updateState(state: S) {
        viewModelScope.launch {
            mutableScreenStateFlow.emit(state)
        }
    }

    protected fun sendAction(action: A) {
        viewModelScope.launch {
            mutableScreenActionChannel.send(action)
        }
    }
}