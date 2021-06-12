package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.entities.RestInterval
import com.vinapp.intervaltrainingtimer.entities.WorkInterval
import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.mvp.view.IntervalKeyboardContract

class IntervalKeyboardPresenter(private val eventHandler: SectionsEventHandler, private val onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener, private val interval: Interval?): IntervalKeyboardContract.Presenter() {

    private val valueLength = 4
    private val timeValue: Array<Int?>
    private var selectedNumberIndex = 0
    private var intervalType = IntervalType.WORK

    init {
        if (interval != null) {
            timeValue = convertToArray(interval.duration)
        } else {
            timeValue = arrayOfNulls(valueLength)
        }
    }

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

    override fun onRestButtonClick() {
        intervalType = IntervalType.REST
        view!!.showSelectedType(intervalType)
    }

    override fun onWorkButtonClick() {
        intervalType = IntervalType.WORK
        view!!.showSelectedType(intervalType)
    }

    override fun onOkButtonClick() {
        val interval: Interval
        when (intervalType) {
            IntervalType.REST -> {
                interval = RestInterval(convertToSeconds(timeValue).toString(), convertToSeconds(timeValue))
            }
            IntervalType.WORK -> {
                interval = WorkInterval(convertToSeconds(timeValue).toString(), convertToSeconds(timeValue))
            }
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
        setIntervalType()
        view!!.showTimeValue(getTimeValueString(), timeValue)
        view!!.showSelectedType(intervalType)
    }

    override fun destroy() {
    }

    private fun setSelectedNumberIndex() {
        if (timeValue.last() != null) {
            selectedNumberIndex = timeValue.lastIndex + 1
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

    private fun setIntervalType() {
        when (interval) {
            is WorkInterval -> intervalType = IntervalType.WORK
            is RestInterval -> intervalType = IntervalType.REST
            else -> intervalType = IntervalType.WORK
        }
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

    private fun convertToArray(duration: Int): Array<Int?> {
        val result: Array<Int?> = arrayOfNulls(valueLength)
        val minutes = duration / 60
        val seconds = duration - minutes * 60
        result[0] = minutes / 10
        result[1] = minutes % 10
        result[2] = seconds / 10
        result[3] = seconds % 10
        return result
    }
}