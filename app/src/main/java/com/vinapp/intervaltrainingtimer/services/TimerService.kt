package com.vinapp.intervaltrainingtimer.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.utils.IntervalTimer

class TimerService: Service() {

    private val binder = TimerBinder()
    private var timer: Timer? = null
    private var intervalTimer: IntervalTimer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    fun start(timer: Timer, intervalTimer: IntervalTimer) {
        this.timer = timer
        this.intervalTimer = intervalTimer
        intervalTimer.start(timer)
    }

    fun pause() {
        intervalTimer?.pause()
    }

    fun resume() {
        intervalTimer?.resume()
    }

    fun stop() {
        intervalTimer?.stop()
    }

    fun restart() {
        intervalTimer?.stop()
        intervalTimer?.start(timer!!)
    }

    inner class TimerBinder: Binder() {

        fun getService(): TimerService {
            return this@TimerService
        }
    }
}