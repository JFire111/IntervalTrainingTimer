package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

interface IntervalsSectionEventListener {

    fun onIntervalClick(interval: Interval, onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener)

    fun onAddIntervalClick(onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener)

    fun onSaveTimerClick(timer: Timer)

    fun onStartTimerClick(timer: Timer)
}