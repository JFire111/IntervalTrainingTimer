package com.vinapp.intervaltrainingtimer.logic.timer

import android.os.CountDownTimer
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer

class TimerInteractor(private val timer: Timer, private var timerOutput: TimerOutput?): TimerInput {

    private var timerDuration: Int = timer.getDuration()
    private val oneSecond: Long = 1000
    private var countDownTimer: CountDownTimer? = null
    private var remainingTime: Int? = null
    private var currentIntervalIndex: Int? = null

    override fun start() {
        remainingTime = timerDuration
        currentIntervalIndex = 0
        startCountDown(timer.intervals[currentIntervalIndex!!])
    }

    override fun pause() {
        countDownTimer!!.cancel()
    }

    override fun resume() {
        var intervalRemainingTime = timer.intervals[currentIntervalIndex!!]
    }

    override fun stop() {
    }

    override fun restart() {
    }

    private fun startCountDown(interval: Interval) {
        timerOutput!!.provideCurrentInterval(interval)
        countDownTimer = object : CountDownTimer(interval.duration * oneSecond, oneSecond) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = remainingTime!! - 1
                timerOutput!!.provideTime(remainingTime!!)
            }

            override fun onFinish() {
                if (currentIntervalIndex!! < timer.intervals.size - 1) {
                    currentIntervalIndex = currentIntervalIndex!! + 1
                    startCountDown(timer.intervals[currentIntervalIndex!!])
                }
            }
        }
    }

    private fun resumeCountDown(interval: Interval, intervalRemainingTime: Long) {
        timerOutput!!.provideCurrentInterval(interval)
        countDownTimer = object : CountDownTimer(intervalRemainingTime, oneSecond) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = remainingTime!! - 1
                timerOutput!!.provideTime(remainingTime!!)
            }

            override fun onFinish() {
                if (currentIntervalIndex!! < timer.intervals.size - 1) {
                    currentIntervalIndex = currentIntervalIndex!! + 1
                    startCountDown(timer.intervals[currentIntervalIndex!!])
                }
            }
        }
    }
}