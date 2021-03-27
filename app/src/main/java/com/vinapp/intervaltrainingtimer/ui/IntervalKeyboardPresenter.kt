package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.view.IntervalKeyboardContract

class IntervalKeyboardPresenter(private val eventHandler: SectionsEventHandler, private val onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener): IntervalKeyboardContract.Presenter() {

    override fun onSaveButtonClick(duration: Int) {
        val interval = object : Interval {
            override var name = duration.toString()
        }
        onIntervalKeyboardListener.onSave(interval)
        eventHandler.onCloseIntervalKeyboard()
    }

    override fun onCancelButtonClick() {
        onIntervalKeyboardListener.onCancel()
        eventHandler.onCloseIntervalKeyboard()
    }

    override fun destroy() {
    }
}