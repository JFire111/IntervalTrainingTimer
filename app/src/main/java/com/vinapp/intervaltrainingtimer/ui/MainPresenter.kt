package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListInput
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingInput
import com.vinapp.intervaltrainingtimer.mvp.MainContract

class MainPresenter(override var currentSection: Int,
                    private val timerEditingInput: TimerEditingInput,
                    private val timerListInput: TimerListInput) : MainContract.Presenter() {

    var sideButtonsClickListener: SideButtonsClickListener? = null

    override fun onStartButtonClick() {
    }

    override fun sectionSelected(section: Int, sideButtonsClickListener: SideButtonsClickListener) {
        this.currentSection = section
        this.sideButtonsClickListener = sideButtonsClickListener
        when (section) {
            0 -> {
                view!!.showLeftButton("Clear")
                view!!.showRightButton("Save")
            }
            1 -> {
                timerListInput.openTimerList()
                view!!.showLeftButton("Edit")
                view!!.hideRightButton()
            }
        }
        view!!.showStartButton()
    }

    override fun sectionScrolled() {
        view!!.hideLeftButton()
        view!!.hideRightButton()
        view!!.hideStartButton()
    }

    override fun onLeftButtonClick() {
        sideButtonsClickListener?.onLeftButtonClick()
    }

    override fun onRightButtonClick() {
        sideButtonsClickListener?.onRightButtonClick()
    }

    override fun detachView() {
    }

    override fun destroy() {
    }

    override fun onIntervalClick(intervalPosition: Int) {
        val interval = timerEditingInput!!.getInterval(intervalPosition)
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                timerEditingInput!!.updateInterval(intervalPosition, interval)
            }

            override fun onCancel() {
            }
        }
        view!!.showIntervalKeyboard(interval, onIntervalKeyboardListener)
    }

    override fun onAddIntervalClick() {
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                timerEditingInput!!.addInterval(interval)
            }

            override fun onCancel() {
            }
        }
        view!!.showIntervalKeyboard(null, onIntervalKeyboardListener)
    }

    override fun onCloseIntervalKeyboard() {
        view!!.hideIntervalKeyboard()
    }

    override fun onAddTimerClick() {
        view!!.showSection(0)
    }

    override fun onTimerClick(position: Int?) {
        timerListInput.selectTimer(position)
    }

    override fun onEditTimerClick(timerId: Int) {
        timerEditingInput.setTimerForEditing(timerId)
        view!!.showSection(0)
    }

    override fun onSaveTimerClick() {
        timerEditingInput.createTimer()
        timerListInput.openTimerList()
        view!!.showSection(1)
    }

}