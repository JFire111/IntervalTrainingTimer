package com.vinapp.intervaltrainingtimer.mvp.model

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerMVPModel {

    fun addTimer(timer: Timer)

    fun getTimers(): List<Timer>

    fun getTimerById(id: Int): Timer?

    fun updateTimer(timer: Timer)

    fun deleteTimer(id: Int)
}