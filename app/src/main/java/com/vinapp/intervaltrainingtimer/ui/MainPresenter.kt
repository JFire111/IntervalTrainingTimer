package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.timer_list.TimerListInput
import com.vinapp.intervaltrainingtimer.logic.timer_editing.TimerEditingInput
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

    override fun sectionSelected(section: Int, onActionButtonsClickListener: OnActionButtonsClickListener) {
        this.currentSection = section
        this.onActionButtonsClickListener = onActionButtonsClickListener
        when (section) {
            0 -> {
                view!!.showClearButton()
                view!!.showSaveButton()
            }
            1 -> {
                timerListInput.getTimerList()
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
        view!!.showSection(0)
    }

    override fun onEditTimerClick(timer: Timer) {
        timerEditingInput.setTimer(timer)
        view!!.showSection(0)
    }

    override fun onDeleteTimerClick(timerId: Int) {
        timerListInput.deleteTimer(timerId)
    }

    override fun onClearTimerClick() {
    }

    override fun onSaveTimerClick(timer: Timer) {
        timerEditingInput.saveTimer(timer)
        timerListInput.getTimerList()
        view!!.showSection(1)
    }

    override fun onStartTimerClick(timer: Timer) {
        view!!.showTimerScreen(timer, serviceController)
    }
}