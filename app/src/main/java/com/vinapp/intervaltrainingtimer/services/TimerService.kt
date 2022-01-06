package com.vinapp.intervaltrainingtimer.services

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Binder
import android.os.IBinder
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.utils.IntervalTimer

class TimerService: Service() {

    private val binder = TimerBinder()
    private var timer: Timer? = null
    private var intervalTimer: IntervalTimer? = null
    private var soundPool: SoundPool? = null
    private var soundId: Int? = null

    override fun onCreate() {
        super.onCreate()
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()
        soundId = soundPool!!.load(this, R.raw.round_beep, 1)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        soundPool?.release()
        soundPool = null
        super.onDestroy()
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

    fun playEndIntervalSound() {
        soundId?.let {
            soundPool?.play(it, 1F, 1F, 0, 0, 1F)
        }
    }

    fun playFinishSound() {
        soundId?.let {
            soundPool?.play(it, 1F, 1F, 0, 2, 1F)
        }
    }

    inner class TimerBinder: Binder() {
        fun getService(): TimerService {
            return this@TimerService
        }
    }
}