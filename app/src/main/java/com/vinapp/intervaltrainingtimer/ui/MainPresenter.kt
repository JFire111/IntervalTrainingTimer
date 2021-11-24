package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListInput
import com.vinapp.intervaltrainingtimer.logic.timer.TimerInteractor
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingInput
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingInteractor
import com.vinapp.intervaltrainingtimer.mvp.MainContract

class MainPresenter(override var currentSection: Int,
                    private val timerEditingInput: TimerEditingInput,
                    private val timerListInput: TimerListInput) : MainContract.Presenter() {

    var sideButtonsClickListener: SideButtonsClickListener? = null
    var isNewTimer: Boolean = true
    var selectedTimer: Timer? = null

    override fun onStartButtonClick() {
        if (currentSection == 0) {
            timerEditingInput.saveTimer()
        }
        view!!.showTimerScreen(TimerInteractor(selectedTimer!!, null))
    }

    override fun sectionSelected(section: Int, sideButtonsClickListener: SideButtonsClickListener) {
        this.currentSection = section
        this.sideButtonsClickListener = sideButtonsClickListener
        when (section) {
            0 -> {
                if (isNewTimer) {
                    view!!.showLeftButton("Clear")
                } else {
                    view!!.showLeftButton("Cancel")
                }
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

    override fun onAddRoundClick() {
        timerEditingInput.addRound()
    }

    override fun onRemoveRoundClick() {
        timerEditingInput.removeRound()
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
        view!!.showIntervalKeyboard(interval, null, onIntervalKeyboardListener)
    }

    override fun onAddIntervalClick() {
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                timerEditingInput!!.addInterval(interval)
            }

            override fun onCancel() {
            }
        }
        view!!.showIntervalKeyboard(null, timerEditingInput.getDefaultIntervalName(), onIntervalKeyboardListener)
    }

    override fun onDeleteIntervalClick(intervalPosition: Int) {
        timerEditingInput.deleteInterval(intervalPosition)
    }

    override fun onCloseIntervalKeyboard() {
        view!!.hideIntervalKeyboard()
    }

    override fun onAddTimerClick() {
        isNewTimer = true
        view!!.showSection(0)
    }

    override fun onTimerClick(timer: Timer) {
        selectedTimer = timer
        timerListInput.selectTimer(timer)
    }

    override fun onEditTimerClick(timer: Timer) {
        selectedTimer = timer
        timerEditingInput.setTimerForEditing(timer)
        isNewTimer = false
        view!!.showSection(0)
    }

    override fun onDeleteTimerClick(timerId: Int) {
        timerListInput.deleteTimer(timerId)
    }

    override fun onClearTimerClick() {
        if (isNewTimer) {
            timerEditingInput.clear()
        } else {
            timerEditingInput.cancelEditing()
            view!!.showSection(1)
        }
    }

    override fun onSaveTimerClick() {
        timerEditingInput.saveTimer()
        timerListInput.openTimerList()
        isNewTimer = true
        view!!.showSection(1)
    }

    override fun setTimer(timer: Timer) {
        this.selectedTimer = timer
    }

}