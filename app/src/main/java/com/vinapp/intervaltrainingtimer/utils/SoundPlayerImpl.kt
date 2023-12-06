package com.vinapp.intervaltrainingtimer.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.vinapp.intervaltrainingtimer.R

class SoundPlayerImpl(context: Context) : SoundPlayer {

    private val attributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()
    private val soundPool = SoundPool.Builder()
        .setAudioAttributes(attributes)
        .setMaxStreams(1)
        .build()

    private var soundId: Int

    init {
        soundId = soundPool.load(context, R.raw.round_beep, 1)
    }

    override fun play(loops: Int) {
        soundPool.play(soundId, 1F, 1F, 1, loops, 1F)
    }
}