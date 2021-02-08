package com.vinapp.intervaltrainingtimer.mvp.model

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerModel {

    fun addTimer(timer: Timer)

    fun getTimers(): List<Timer>

    fun deleteTimer(timer: Timer)
}