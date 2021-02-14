package com.vinapp.intervaltrainingtimer.mvp.model

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerModel {

    fun addTimer(timer: Timer)

    fun getTimers(): ArrayList<Timer>

    fun deleteTimer(timer: Timer)
}