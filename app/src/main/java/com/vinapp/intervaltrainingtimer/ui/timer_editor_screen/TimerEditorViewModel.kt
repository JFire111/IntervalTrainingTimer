package com.vinapp.intervaltrainingtimer.ui.timer_editor_screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.vinapp.intervaltrainingtimer.App
import com.vinapp.intervaltrainingtimer.base.presentation.BaseViewModel
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.data.interval.IntervalRepository
import com.vinapp.intervaltrainingtimer.data.timer.TimerRepository
import com.vinapp.intervaltrainingtimer.domain.Interval
import com.vinapp.intervaltrainingtimer.domain.Timer
import com.vinapp.intervaltrainingtimer.mapping.IntervalMapper.mapIntervalToIntervalItemData
import com.vinapp.intervaltrainingtimer.utils.TimeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val MIN_NUMBER_OF_ROUNDS = 1
private const val MAX_NUMBER_OF_ROUNDS = 100
private const val MIN_START_DELAY = 0
private const val MAX_START_DELAY = 60
private const val MIN_TIME_BETWEEN_ROUNDS = 0

class TimerEditorViewModel(
    val intervalRepository: IntervalRepository,
    val timerRepository: TimerRepository,
) : BaseViewModel<TimerEditorScreenState, TimerEditorScreenAction>() {

    override val mutableScreenStateFlow = MutableStateFlow(TimerEditorScreenState())
    override val mutableScreenActionChannel = Channel<TimerEditorScreenAction>()

    private var timer: Timer? = null
    private var intervalList: MutableList<Interval> = mutableListOf()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as App
                TimerEditorViewModel(
                    intervalRepository = app.container.intervalRepository,
                    timerRepository = app.container.timerRepository
                )
            }
        }
    }

    fun loadTimerById(timerId: String?) {
        if (timerId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                timer = timerRepository.getTimerById(timerId)
                timer?.let {
                    updateState(
                        currentScreenState.copy(
                            timerName = it.name,
                            intervalList = it.intervalList.map(::mapIntervalToIntervalItemData),
                            numberOfRounds = it.numberOfRounds,
                        )
                    )
                }
            }
        }
    }

    fun onIncreaseRoundsClick() {
        var numberOfRounds = currentScreenState.numberOfRounds + 1
        if (numberOfRounds > MAX_NUMBER_OF_ROUNDS) {
            numberOfRounds = MIN_NUMBER_OF_ROUNDS
        }
        updateState(
            currentScreenState.copy(
                totalTimeDigits = TimeConverter.getTimeDigits(
                    numberOfRounds = numberOfRounds,
                    intervalList = intervalList,
                    timeBetweenRounds = currentScreenState.timeBetweenRounds
                ),
                numberOfRounds = numberOfRounds,
            )
        )
    }

    fun onDecreaseRoundsClick() {
        var numberOfRounds = currentScreenState.numberOfRounds - 1
        if (numberOfRounds < MIN_NUMBER_OF_ROUNDS) {
            numberOfRounds = MAX_NUMBER_OF_ROUNDS
        }
        updateState(
            currentScreenState.copy(
                totalTimeDigits = TimeConverter.getTimeDigits(
                    numberOfRounds = numberOfRounds,
                    intervalList = intervalList,
                    timeBetweenRounds = currentScreenState.timeBetweenRounds
                ),
                numberOfRounds = numberOfRounds,
            )
        )
    }

    fun onIncreaseStartDelayClick() {
        var startDelay = currentScreenState.startDelay + 1
        if (startDelay > MAX_START_DELAY) {
            startDelay = MIN_START_DELAY
        }
        updateState(
            currentScreenState.copy(
                startDelay = startDelay
            )
        )
    }

    fun onDecreaseStartDelayClick() {
        var startDelay = currentScreenState.startDelay - 1
        if (startDelay < MIN_START_DELAY) {
            startDelay = MAX_START_DELAY
        }
        updateState(
            currentScreenState.copy(
                startDelay = startDelay
            )
        )
    }

    fun onIncreaseTimeBetweenRoundsClick() {
        var timeBetweenRounds = currentScreenState.timeBetweenRounds + 1
        updateState(
            currentScreenState.copy(
                totalTimeDigits = TimeConverter.getTimeDigits(
                    numberOfRounds = currentScreenState.numberOfRounds,
                    intervalList = intervalList,
                    timeBetweenRounds = timeBetweenRounds,
                ),
                timeBetweenRounds = timeBetweenRounds,
            )
        )
    }

    fun onDecreaseTimeBetweenRoundsClick() {
        var timeBetweenRounds = currentScreenState.timeBetweenRounds - 1
        if (timeBetweenRounds >= MIN_TIME_BETWEEN_ROUNDS) {
            updateState(
                currentScreenState.copy(
                    totalTimeDigits = TimeConverter.getTimeDigits(
                        numberOfRounds = currentScreenState.numberOfRounds,
                        intervalList = intervalList,
                        timeBetweenRounds = timeBetweenRounds,
                    ),
                    timeBetweenRounds = timeBetweenRounds,
                )
            )
        }
    }

    fun onIntervalClick(index: Int) {
        updateState(
            currentScreenState.copy(
                selectedInterval = intervalList[index],
                showIntervalDialog = true
            )
        )
    }

    fun onAddIntervalClick() {
        updateState(
            currentScreenState.copy(
                selectedInterval = null,
                showIntervalDialog = true
            )
        )
    }

    fun onSaveIntervalClick(name: String, duration: Long, color: IntervalColor) {
        val selectedInterval = currentScreenState.selectedInterval
        if (selectedInterval != null) {
            intervalList[intervalList.indexOf(selectedInterval)] = selectedInterval.copy(
                name = name,
                duration = duration,
                color = color
            )
        } else {
            intervalList.add(
                Interval(
                    id = getNewIntervalId(),
                    timerId = "",
                    name = name,
                    duration = duration,
                    color = color,
                )
            )
        }
        updateState(
            currentScreenState.copy(
                totalTimeDigits = TimeConverter.getTimeDigits(
                    numberOfRounds = currentScreenState.numberOfRounds,
                    intervalList = intervalList,
                    timeBetweenRounds = currentScreenState.timeBetweenRounds
                ),
                intervalList = intervalList.map(::mapIntervalToIntervalItemData),
                showIntervalDialog = false
            )
        )
    }

    fun closeDialog() {
        updateState(
            currentScreenState.copy(
                selectedInterval = null,
                showIntervalDialog = false
            )
        )
    }

    private fun getNewIntervalId(): Int {
        return intervalList.maxByOrNull { it.id }?.let {
            it.id + 1
        } ?: 0
    }
}