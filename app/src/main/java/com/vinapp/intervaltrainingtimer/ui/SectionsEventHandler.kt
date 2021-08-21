package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.base.Interval

interface SectionsEventHandler {

    var currentSection: Int

    fun onIntervalClick(interval: Interval, onIntervalKeyboardListener: OnIntervalKeyboardListener)

    fun onAddIntervalClick(onIntervalKeyboardListener: OnIntervalKeyboardListener)

    fun onCloseIntervalKeyboard()

    fun onAddTimerClick()

    interface OnIntervalKeyboardListener {

        fun onSave(interval: Interval)

        fun onCancel()
    }
}