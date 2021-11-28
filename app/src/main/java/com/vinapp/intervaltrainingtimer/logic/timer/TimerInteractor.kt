package com.vinapp.intervaltrainingtimer.logic.timer

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.utils.IntervalTimer

class TimerInteractor(private val timer: Timer, private var timerOutput: TimerOutput?): TimerInput {

    private var timerDuration: Int = timer.getDuration()
    private var remainingTime: Int? = null
    private val intervalTimer: IntervalTimer = object : IntervalTimer(timer) {
        override fun onTick(time: Long) {
            timerOutput?.provideTime(time)
        }

        override fun onIntervalEnded(endedIntervalIndex: Int) {
            if (timer.intervals.size > endedIntervalIndex + 1) {
                timerOutput?.provideCurrentInterval(timer.intervals[endedIntervalIndex + 1])
            }
        }

        override fun onRoundEnded(remainingRounds: Int) {
        }

        override fun onFinish() {
            timerOutput?.provideState(TimerState.FINISHED)
        }
    }

    override fun start() {
        timerOutput?.provideCurrentInterval(timer.intervals.first())
        timerOutput?.provideState(TimerState.IN_PROGRESS)
        intervalTimer.start()
    }

    override fun pause() {
        timerOutput?.provideState(TimerState.PAUSED)
        intervalTimer.pause()
    }

    override fun resume() {
        timerOutput?.provideState(TimerState.IN_PROGRESS)
        intervalTimer.resume()
    }

    override fun stop() {
        timerOutput?.provideState(TimerState.STOPPED)
        intervalTimer.stop()
    }

    override fun restart() {
        intervalTimer.stop()
        intervalTimer.start()
    }

    override fun registerOutput(output: TimerOutput) {
        this.timerOutput = output
        timerOutput?.provideTime(remainingTime?.toLong() ?: timerDuration.toLong())
    }

    override fun unregisterOutput() {
        this.timerOutput = null
    }
}