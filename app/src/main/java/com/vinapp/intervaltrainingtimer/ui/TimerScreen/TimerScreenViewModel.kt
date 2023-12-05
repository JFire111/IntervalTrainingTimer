package com.vinapp.intervaltrainingtimer.ui.TimerScreen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.vinapp.intervaltrainingtimer.App
import com.vinapp.intervaltrainingtimer.base.presentation.BaseViewModel
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.data.timer.TimerRepository
import com.vinapp.intervaltrainingtimer.domain.Interval
import com.vinapp.intervaltrainingtimer.domain.Timer
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.mapping.IntervalMapper.mapIntervalToIntervalItemData
import com.vinapp.intervaltrainingtimer.utils.IntervalTimerControl
import com.vinapp.intervaltrainingtimer.utils.IntervalTimerNew
import com.vinapp.intervaltrainingtimer.utils.IntervalTimerNewImpl
import com.vinapp.intervaltrainingtimer.utils.TimeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TimerScreenViewModel(
    private val timerRepository: TimerRepository
) : BaseViewModel<TimerScreenState, TimerScreenAction>() {

    override val mutableScreenStateFlow = MutableStateFlow(TimerScreenState())
    override val mutableScreenActionChannel = Channel<TimerScreenAction>()

    private var timer: Timer? = null
    private var intervalTimer: IntervalTimerNew? = null
    private var timerControl: IntervalTimerControl? = null

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App
                TimerScreenViewModel(
                    timerRepository = app.container.timerRepository
                )
            }
        }
    }

    fun loadTimer(timerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            timer = timerRepository.getTimerById(timerId)
            timer?.let { timer ->
                updateState(
                    currentScreenState.copy(
                        timerName = timer.name,
                        remainingTime = TimeConverter.getTimeString(
                            numberOfRounds = timer.numberOfRounds,
                            intervalList = timer.intervalList,
                            timeBetweenRounds = timer.timeBetweenRounds
                        )
                    )
                )
                if (intervalTimer == null) {
                    initTimer(timer)
                }
            }
        }
    }

    fun onCenterActionButtonClick() {
        when (currentScreenState.timerState) {
            TimerState.INITIALIZED -> startTimer()
            TimerState.IN_PROGRESS -> pauseTimer()
            TimerState.PAUSED -> resumeTimer()
            TimerState.STOPPED -> stopTimer()
            TimerState.FINISHED -> restartTimer()
        }
    }

    private fun startTimer() {
        intervalTimer?.let {
            updateState(
                currentScreenState.copy(
                    timerState = TimerState.IN_PROGRESS
                )
            )
            it.start()
        }
    }

    private fun pauseTimer() {
        intervalTimer?.let {
            updateState(
                currentScreenState.copy(
                    timerState = TimerState.PAUSED
                )
            )
            it.pause()
        }
    }

    private fun resumeTimer() {
        intervalTimer?.let {
            updateState(
                currentScreenState.copy(
                    timerState = TimerState.IN_PROGRESS
                )
            )
            it.resume()
        }
    }

    private fun stopTimer() {
        intervalTimer?.let {
            updateState(
                currentScreenState.copy(
                    timerState = TimerState.STOPPED
                )
            )
            it.stop()
        }
    }

    private fun restartTimer() {
        intervalTimer?.let {
            updateState(
                currentScreenState.copy(
                    timerState = TimerState.IN_PROGRESS
                )
            )
            it.restart()
        }
    }

    private fun initTimer(
        timer: Timer,
    ) {
        if (timerControl == null) {
            initTimerControl(timer)
        }
        timerControl?.let { control ->
            intervalTimer = IntervalTimerNewImpl(
                timer = timer,
                control = control
            )
        }
    }

    private fun initTimerControl(timer: Timer) {
        timerControl = object : IntervalTimerControl {
            override fun onTick(time: Long) {
                updateState(
                    currentScreenState.copy(
                        remainingTime = TimeConverter.getTimeStringFromMillis(time)
                    )
                )
            }

            override fun onIntervalChanged(intervalIndex: Int?) {
                updateState(
                    currentScreenState.copy(
                        backgroundColor = intervalIndex?.let {
                            timer.intervalList[it].color
                        } ?: IntervalColor.YELLOW
                    )
                )
            }

            override fun onRoundChanged(currentRound: Int) {}

            override fun onFinish() {
                updateState(
                    currentScreenState.copy(
                        backgroundColor = IntervalColor.WHITE,
                        remainingTime = "00:00",
                        timerState = TimerState.FINISHED
                    )
                )
            }
        }
    }
}