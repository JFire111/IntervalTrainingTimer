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
    OnActionButtonsClickListener,
    IntervalsSectionEventListener,
    TimersSectionEventListener {

    var onActionButtonsClickListener: OnActionButtonsClickListener? = null
    var isNewTimer: Boolean = true
    var selectedTimer: Timer? = null

    override fun sectionSelected(section: Int, onActionButtonsClickListener: OnActionButtonsClickListener) {
        this.currentSection = section
        this.onActionButtonsClickListener = onActionButtonsClickListener
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
        onActionButtonsClickListener!!.onStartButtonClick()
    }

    override fun onLeftButtonClick() {
        onActionButtonsClickListener!!.onLeftButtonClick()
    }

    override fun onRightButtonClick() {
        onActionButtonsClickListener!!.onRightButtonClick()
    }

    override fun detachView() {
    }

    override fun destroy() {
    }

    override fun onIntervalClick(interval: Interval, onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener) {
        view!!.showIntervalKeyboard(interval, onIntervalKeyboardListener)
    }

    override fun onAddIntervalClick(onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener) {
        view!!.showIntervalKeyboard(null, onIntervalKeyboardListener)
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

    override fun onSaveTimerClick(timer: Timer) {
        timerEditingInput.saveTimer(timer)
        timerListInput.openTimerList()
        isNewTimer = true
        view!!.showSection(1)
    }

    override fun setTimer(timer: Timer) {
        this.selectedTimer = timer
    }

    override fun onStartTimerClick(timer: Timer) {
        view!!.showTimerScreen(timer, serviceController)
    }
}