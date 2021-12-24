package com.vinapp.intervaltrainingtimer.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.timer.TimerOutput
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.utils.IntervalTimer

class TimerServiceController(private val applicationContext: Context) {

    private var timerService: TimerService? = null
    private var bound = false
    private var output: TimerOutput? = null
    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            timerService = (binder as TimerService.TimerBinder).getService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }
    }
    private val intervalTimer = object : IntervalTimer() {
        override fun onTick(time: Long) {
            output?.provideTime(time)
        }

        override fun onIntervalStart(endedIntervalIndex: Int) {
            output?.provideCurrentInterval(endedIntervalIndex)
        }

        override fun onRoundEnded(remainingRounds: Int) {
        }

        override fun onFinish() {
            output?.provideState(TimerState.FINISHED)
        }
    }

    fun bindService(serviceClass: Class<*>?) {
        var intent = Intent(applicationContext, serviceClass)
        applicationContext.bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
    }

    fun unbindService() {
        if (bound) {
            applicationContext.unbindService(serviceConnection!!)
        }
        bound = false
    }

    fun registerOutput(timerOutput: TimerOutput) {
        this.output = timerOutput
    }

    fun unregisterOutput() {
        this.output = null
    }

    fun start(timer: Timer) {
        timerService?.start(timer, intervalTimer)
        output?.provideState(TimerState.IN_PROGRESS)
        output?.provideTime(timer.getDurationInMillis())
    }

    fun pause() {
        output?.provideState(TimerState.PAUSED)
        timerService?.pause()
    }

    fun resume() {
        output?.provideState(TimerState.IN_PROGRESS)
        timerService?.resume()
    }

    fun stop() {
        output?.provideState(TimerState.STOPPED)
        timerService?.stop()
    }

    fun restart() {
        output?.provideState(TimerState.IN_PROGRESS)
        timerService?.restart()
    }
}