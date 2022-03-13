package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimersSectionEventListener {

    fun onAddTimerClick()

    fun onEditTimerClick(timer: Timer)

    fun onDeleteTimerClick(timerId: Int)

    fun onStartTimerClick(timer: Timer)
}