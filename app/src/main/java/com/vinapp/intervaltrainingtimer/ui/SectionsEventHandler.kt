package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.base.Interval

interface SectionsEventHandler {

    var currentSection: Int

    fun onIntervalClick(intervalPosition: Int)

    fun onAddIntervalClick()

    fun onCloseIntervalKeyboard()

    fun onAddTimerClick()

    fun onTimerClick(position: Int)

    fun onSaveTimerClick()

    interface OnIntervalKeyboardListener {

        fun onSave(interval: Interval)

        fun onCancel()
    }
}