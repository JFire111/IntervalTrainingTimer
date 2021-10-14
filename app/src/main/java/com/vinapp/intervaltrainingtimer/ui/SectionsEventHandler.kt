package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer

interface SectionsEventHandler {

    var currentSection: Int

    fun onAddRoundClick()

    fun onRemoveRoundClick()

    fun onIntervalClick(intervalPosition: Int)

    fun onAddIntervalClick()

    fun onDeleteIntervalClick(intervalPosition: Int)

    fun onCloseIntervalKeyboard()

    fun onAddTimerClick()

    fun onTimerClick(timerPosition: Int)

    fun onEditTimerClick(timer: Timer)

    fun onDeleteTimerClick(timerId: Int)

    fun onClearTimerClick()

    fun onSaveTimerClick()

    interface OnIntervalKeyboardListener {

        fun onSave(interval: Interval)

        fun onCancel()
    }
}