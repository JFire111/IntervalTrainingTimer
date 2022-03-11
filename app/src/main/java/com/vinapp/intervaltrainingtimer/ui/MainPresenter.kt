package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListInput
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingInput
import com.vinapp.intervaltrainingtimer.mvp.MainContract
import com.vinapp.intervaltrainingtimer.services.TimerServiceController
import com.vinapp.intervaltrainingtimer.ui.sections.IntervalsSectionEventListener
import com.vinapp.intervaltrainingtimer.ui.sections.TimersSectionEventListener

class MainPresenter(
    override var currentSection: Int,
    private val timerEditingInput: TimerEditingInput,
    private val timerListInput: TimerListInput,
    private val serviceController: TimerServiceController
    ) : MainContract.Presenter(),
    IntervalsSectionEventListener,
    TimersSectionEventListener {

    var sideButtonsClickListener: SideButtonsClickListener? = null
    var isNewTimer: Boolean = true
    var selectedTimer: Timer? = null

    override fun sectionSelected(section: Int, sideButtonsClickListener: SideButtonsClickListener) {
        this.currentSection = section
        this.sideButtonsClickListener = sideButtonsClickListener
        when (section) {
            0 -> {
                if (isNewTimer) {
                    view!!.showClearButton()
                } else {
                    //view!!.showLeftButton("Cancel")
                }
                view!!.showSaveButton()
            }
            1 -> {
                timerListInput.openTimerList()
                view!!.showEditButton()
            }
        }
        view!!.showStartButton()
    }

    override fun sectionScrolled() {
        view!!.hideClearButton()
        view!!.hideEditButton()
        view!!.hideSaveButton()
        view!!.hideStartButton()
    }

    override fun onStartButtonClick() {
        if (currentSection == 0) {
            timerEditingInput.saveTimer()
        }
        view!!.showTimerScreen(selectedTimer!!, serviceController)
    }

    override fun onClearButtonClick() {
        sideButtonsClickListener?.onLeftButtonClick()
    }

    override fun onSaveButtonClick() {
        sideButtonsClickListener?.onRightButtonClick()
    }

    override fun onEditButtonClick() {
        sideButtonsClickListener?.onLeftButtonClick()
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
        val interval = timerEditingInput.getInterval(intervalPosition)
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                timerEditingInput.updateInterval(intervalPosition, interval)
            }

            override fun onCancel() {
            }
        }
        view!!.showIntervalKeyboard(interval, onIntervalKeyboardListener)
    }

    override fun onAddIntervalClick() {
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                timerEditingInput.addInterval(interval)
            }

            override fun onCancel() {
            }
        }
        view!!.showIntervalKeyboard(null, onIntervalKeyboardListener)
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

    override fun onSaveTimerClick(timerName: String) {
        timerEditingInput.saveTimer()
        timerListInput.openTimerList()
        isNewTimer = true
        view!!.showSection(1)
    }

    override fun setTimer(timer: Timer) {
        this.selectedTimer = timer
    }

}