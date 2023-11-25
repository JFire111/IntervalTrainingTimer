package com.vinapp.intervaltrainingtimer.mvp.model

import com.vinapp.intervaltrainingtimer.entities.TimerEntity

interface TimerMVPModel {

    fun addTimer(timer: TimerEntity)

    fun getTimers(): List<TimerEntity>

    fun getTimerById(id: Int): TimerEntity?

    fun updateTimer(timer: TimerEntity)

    fun deleteTimer(id: Int)
}