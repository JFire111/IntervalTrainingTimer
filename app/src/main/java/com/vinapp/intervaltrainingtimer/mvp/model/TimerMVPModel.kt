package com.vinapp.intervaltrainingtimer.mvp.model

import com.vinapp.intervaltrainingtimer.entities.base.Timer

interface TimerMVPModel {

    fun addTimer(timer: Timer)

    fun getTimers(): ArrayList<Timer>

    fun getTimerById(id: Int): Timer?

    fun updateTimer(timer: Timer)

    fun deleteTimer(timer: Timer)
}