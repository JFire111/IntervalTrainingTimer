package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer

interface SectionsEventHandler {

    var currentSection: Int

    fun onAddRoundClick()

    fun onRemoveRoundClick()

    fun onIntervalClick(intervalPosition: Int)

    fun onAddIntervalClick()

    fun onCloseIntervalKeyboard()

    fun onAddTimerClick()

    fun onTimerClick(position: Int?)

    fun onEditTimerClick(timer: Timer)

    fun onSaveTimerClick()

    interface OnIntervalKeyboardListener {

        fun onSave(interval: Interval)

        fun onCancel()
    }
}