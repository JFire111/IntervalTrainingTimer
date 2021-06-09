package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.view.IntervalKeyboardContract

class IntervalKeyboardPresenter(private val eventHandler: SectionsEventHandler, private val onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener): IntervalKeyboardContract.Presenter() {

    private val valueLength = 4
    private val timeValue: Array<Int?> = arrayOfNulls(valueLength)
    private var selectedNumberIndex = 0

    override fun onKeyboardButtonClick(number: Int) {
        if (selectedNumberIndex < valueLength) {
            timeValue[selectedNumberIndex] = number
            selectedNumberIndex++
            view!!.showTimeValue(getTimeValueString(), timeValue)
        }
    }

    override fun onDeleteButtonClick() {
        if (selectedNumberIndex <= valueLength) {
            if (selectedNumberIndex > 0) {
                selectedNumberIndex--
            }
            timeValue[selectedNumberIndex] = null
            view!!.showTimeValue(getTimeValueString(), timeValue)
        }
    }

    override fun onSaveButtonClick() {
        val interval = object : Interval {
            override var name = convertToSeconds(timeValue).toString()
        }
        onIntervalKeyboardListener.onSave(interval)
        eventHandler.onCloseIntervalKeyboard()
    }

    override fun onCancelButtonClick() {
        onIntervalKeyboardListener.onCancel()
        eventHandler.onCloseIntervalKeyboard()
    }

    override fun attachView(view: IntervalKeyboardContract.View) {
        super.attachView(view)
        setSelectedNumberIndex()
        view!!.showTimeValue(getTimeValueString(), timeValue)
    }

    override fun destroy() {
    }

    private fun setSelectedNumberIndex() {
        if (timeValue.last() != null) {
            selectedNumberIndex = timeValue.lastIndex
        } else {
            selectedNumberIndex = timeValue.indexOfFirst { it == null }
        }
    }

    private fun getTimeValueString(): String {
        var timeValueString = ""
        timeValue.forEach {
            if (it == null) {
                timeValueString += "0"
            } else {
                timeValueString += "$it"
            }
        }
        timeValueString = timeValueString.substring(0, valueLength / 2) + ":" + timeValueString.substring(valueLength / 2)
        return timeValueString
    }

    private fun convertToSeconds(values: Array<Int?>): Int {
        var minutes = 0
        var seconds = 0
        minutes += (values[0]?: 0) * 10
        minutes += values[1]?: 0
        seconds += (values[2]?: 0) * 10
        seconds += values[3]?: 0
        return minutes * 60 + seconds
    }

    /*private fun convertToArray(time: Int): Array<Int?> {
        var result: Array<Int?> = arrayOfNulls(valueLength)
    }*/
}