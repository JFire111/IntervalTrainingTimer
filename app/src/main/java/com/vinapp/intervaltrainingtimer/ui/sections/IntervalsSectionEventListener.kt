package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Timer

interface IntervalsSectionEventListener {

    fun onAddRoundClick()

    fun onRemoveRoundClick()

    fun onIntervalClick(intervalPosition: Int)

    fun onAddIntervalClick()

    fun onDeleteIntervalClick(intervalPosition: Int)

    fun onClearTimerClick()

    fun onSaveTimerClick(timerName: String)

    fun setTimer(timer: Timer)
}