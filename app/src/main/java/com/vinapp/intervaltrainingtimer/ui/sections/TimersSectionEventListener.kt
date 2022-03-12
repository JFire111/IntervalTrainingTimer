package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimersSectionEventListener {

    fun onAddTimerClick()

    fun onTimerClick(timer: Timer)

    fun onEditTimerClick(timer: Timer)

    fun onDeleteTimerClick(timerId: Int)

    fun setTimer(timer: Timer)

    fun onStartTimerClick(timer: Timer)
}