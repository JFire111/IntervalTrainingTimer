package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer

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

    fun onClearTimerClick()

    fun onSaveTimerClick()

    interface OnIntervalKeyboardListener {

        fun onSave(interval: Interval)

        fun onCancel()
    }
}