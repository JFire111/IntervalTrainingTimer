package com.vinapp.intervaltrainingtimer.logic.gettimers

import com.vinapp.intervaltrainingtimer.entities.base.Timer

interface GetTimers {

    fun getTimersList(): List<Timer>

    fun deleteTimer(timer: Timer)
}