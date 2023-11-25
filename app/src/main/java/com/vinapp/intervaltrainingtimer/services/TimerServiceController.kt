package com.vinapp.intervaltrainingtimer.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.entities.TimerEntity
import com.vinapp.intervaltrainingtimer.logic.timer.TimerOutput
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.utils.IntervalTimer

class TimerServiceController(private val applicationContext: Context) {

    private var intervalTimer: IntervalTimer? = null
    private var timerService: TimerService? = null
    private var bound = false
    private var output: TimerOutput? = null
    private val notificationId = 0
    private val notificationChannelId = "TimerNotification"
    private val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            timerService = (binder as TimerService.TimerBinder).getService()
            bound = true
            initTimer()
            //showNotification()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }
    }

    fun bindService(serviceClass: Class<*>?) {
        var intent = Intent(applicationContext, serviceClass)
        applicationContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService() {
        if (bound) {
            applicationContext.unbindService(serviceConnection)
            //closeNotification()
        }
        bound = false
    }

    fun registerOutput(timerOutput: TimerOutput) {
        this.output = timerOutput
    }

    fun unregisterOutput() {
        this.output = null
    }

    fun setDelay(delay: Long) {
        intervalTimer!!.setStartDelay(delay)
    }

    fun start(timer: TimerEntity) {
        timerService?.start(timer, intervalTimer!!)
        output?.provideState(TimerState.IN_PROGRESS)
//        output?.provideTime(timer.getDurationInMillis())
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

    private fun initTimer() {
        intervalTimer = object : IntervalTimer() {
            override fun onDelayTick(delay: Long) {
                output?.provideDelay(delay)
            }

            override fun onTick(time: Long) {
                output?.provideTime(time)
            }

            override fun onIntervalStart(intervalIndex: Int) {
                output?.provideCurrentInterval(intervalIndex)
            }

            override fun onIntervalEnd(endedIntervalIndex: Int) {
                timerService!!.playEndIntervalSound()
            }

            override fun onRoundStart(remainingRounds: Int) {
            }

            override fun onRoundEnd(remainingRounds: Int) {
            }

            override fun onFinish() {
                output?.provideState(TimerState.FINISHED)
                timerService!!.playFinishSound()
            }
        }
    }

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelId, "TimerNotificationChannel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
//        notificationManager.notify(notificationId, createNotification())
    }

    private fun closeNotification() {
        notificationManager.cancel(notificationId)
    }

    private fun createNotification(): Notification {
        val notificationLayout = RemoteViews(applicationContext.packageName, R.layout.notification)
        return NotificationCompat.Builder(applicationContext, notificationChannelId)
            .setSilent(true)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContent(notificationLayout)
            .build()
    }
}