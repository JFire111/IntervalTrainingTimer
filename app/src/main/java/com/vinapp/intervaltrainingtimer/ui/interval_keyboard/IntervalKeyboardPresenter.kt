package com.vinapp.intervaltrainingtimer.ui.interval_keyboard

import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.view.IntervalKeyboardContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

class IntervalKeyboardPresenter(
    private val eventHandler: SectionsEventHandler,
    private val onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener,
    private val interval: Interval?,
        ): IntervalKeyboardContract.Presenter() {

    private val valueLength = 4
    private val timeValue: Array<Int?>
    private var intervalName: String = ""
    private var selectedNumberIndex = 0
    private var intervalType = IntervalType.WORK

    init {
        if (interval != null) {
            intervalName = interval.name
            timeValue = convertToArray(interval.duration)
        } else {
            intervalName = intervalType.toString()
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
        intervalName = view!!.getIntervalName()
        if (intervalName.equals(IntervalType.WORK.toString())) {
            intervalName = IntervalType.REST.toString()
            view!!.showIntervalName(intervalName)
        }
        view!!.showSelectedType(intervalType)
    }

    override fun onWorkButtonClick() {
        intervalType = IntervalType.WORK
        intervalName = view!!.getIntervalName()
        if (intervalName.equals(IntervalType.REST.toString())) {
            intervalName = IntervalType.WORK.toString()
            view!!.showIntervalName(intervalName)
        }
        view!!.showSelectedType(intervalType)
    }

    override fun onOkButtonClick() {
        intervalName = view!!.getIntervalName()
        val interval = Interval(intervalName, convertToSeconds(timeValue), intervalType)
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
        view.showIntervalName(intervalName)
        view.showTimeValue(getTimeValueString(), timeValue)
        view.showSelectedType(intervalType)
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
        intervalType = interval?.type ?: IntervalType.WORK
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