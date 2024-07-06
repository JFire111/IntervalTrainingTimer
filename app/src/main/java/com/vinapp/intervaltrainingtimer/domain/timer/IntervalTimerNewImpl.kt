package com.vinapp.intervaltrainingtimer.domain.timer

import com.vinapp.intervaltrainingtimer.domain.entities.Timer
import com.vinapp.intervaltrainingtimer.utils.TimeConverter
import kotlinx.coroutines.*

class IntervalTimerNewImpl(
    private val timer: Timer,
    private val control: IntervalTimerControl,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val tickerStepInMillis: Long = 10L,
) : IntervalTimerNew {

    private var timerJob: Job? = null
    private var tickerState = TickerState.STOPPED

    private val initialTime = TimeConverter.getTimeInMillis(
        timer = timer
    )
    private val initialDelayTime = TimeConverter.getTimeInMillis(
        timeInSeconds = timer.startDelay
    )
    private val initialRound = 0
    private val initialIntervalIndex = 0

    private var currentRound = initialRound
    private var currentIntervalIndex = initialIntervalIndex
    private var isTimeBetweenRoundsInterval: Boolean = false

    private var remainingTime = initialTime
    private var remainingDelayTime = initialDelayTime
    private var remainingIntervalTime = TimeConverter.getTimeInMillis(
        timeInSeconds = timer.intervalList[currentIntervalIndex].durationInSeconds
    )

    override fun start() {
        timerJob = coroutineScope.launch {
            if (remainingDelayTime > 0) {
                startTimerDelay()
            }
            control.onRoundEnded(++currentRound)
            control.onIntervalChanged(currentIntervalIndex)
            startTimer()
        }
    }

    override fun pause() {
        stopTicker()
        timerJob?.cancel()
    }

    override fun resume() {
        timerJob?.cancel()
        timerJob = coroutineScope.launch {
            if (remainingDelayTime > 0) {
                startTimerDelay()
            }
            startTimer()
        }
    }

    override fun stop() {
        stopTicker()
        timerJob?.cancel()
        resetValues()
    }

    override fun restart() {
        timerJob?.cancel()
        resetValues()
        start()
    }

    private fun resetValues() {
        currentRound = initialRound
        currentIntervalIndex = initialIntervalIndex
        isTimeBetweenRoundsInterval = false
        remainingTime = initialTime
        remainingDelayTime = initialDelayTime
        remainingIntervalTime = TimeConverter.getTimeInMillis(
            timeInSeconds = timer.intervalList[currentIntervalIndex].durationInSeconds
        )
    }

    private suspend fun startTimerDelay() {
        runTicker(
            onTick = {
                remainingDelayTime -= it
                if (remainingDelayTime <= 0) {
                    stopTicker()
                }
                control.onTick(remainingDelayTime, remainingDelayTime)
            }
        )
    }

    private suspend fun startTimer() {
        runTicker(
            onTick = {
                remainingTime -= it
                remainingIntervalTime -= it
                if (remainingTime > 0) {
                    control.onTick(remainingTime, remainingIntervalTime)
                    if (remainingIntervalTime <= 0) {
                        updateCurrentInterval()
                    }
                } else {
                    stopTicker()
                    control.onFinish()
                }
            }
        )
    }

    private suspend fun runTicker(
        onTick: (Long) -> Unit
    ) {
        tickerState = TickerState.STARTED
        var beforeDelay: Long
        var diff: Long
        while (tickerState == TickerState.STARTED) {
            beforeDelay = System.currentTimeMillis()
            delay(tickerStepInMillis)
            diff = System.currentTimeMillis() - beforeDelay
            onTick(diff)
        }
    }

    private fun stopTicker() {
        tickerState = TickerState.STOPPED
    }

    private fun updateCurrentInterval() {
        if (currentIntervalIndex < timer.intervalList.size - 1) {
            currentIntervalIndex++
            setInterval()
        } else {
            if (!isTimeBetweenRoundsInterval && timer.timeBetweenRounds > 0) {
                setTimeBetweenRoundsInterval()
            } else {
                setNextRound()
            }
        }
    }

    private fun setTimeBetweenRoundsInterval() {
        control.onRoundEnded(++currentRound)
        isTimeBetweenRoundsInterval = true
        remainingIntervalTime = TimeConverter.getTimeInMillis(
            timeInSeconds = timer.timeBetweenRounds
        )
    }

    private fun setInterval() {
        isTimeBetweenRoundsInterval = false
        remainingIntervalTime = TimeConverter.getTimeInMillis(
            timeInSeconds = timer.intervalList[currentIntervalIndex].durationInSeconds
        )
        control.onIntervalChanged(currentIntervalIndex)
    }

    private fun setNextRound() {
        currentIntervalIndex = 0
        setInterval()
    }

    enum class TickerState {
        STARTED,
        STOPPED,
    }
}

enum class TimerState {
    INITIALIZED,
    IN_PROGRESS,
    PAUSED,
    STOPPED,
    FINISHED
}