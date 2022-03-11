package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListOutput
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

class TimersSectionPresenter(override val timersSectionEventListener: TimersSectionEventListener): TimerSectionContract.Presenter(), TimerListOutput, SideButtonsClickListener {

    var selectedTimerPosition: Int? = null
    var timers: ArrayList<Timer> = arrayListOf()

    override fun onTimerItemClick(position: Int) {
        selectedTimerPosition = position
        timersSectionEventListener.onTimerClick(timers[position])
    }

    override fun onAddTimerClick() {
        timersSectionEventListener.onAddTimerClick()
    }

    override fun onDeleteTimerClick(position: Int) {
        timersSectionEventListener.onDeleteTimerClick(timers[position].id)
    }

    override fun attachView(view: TimerSectionContract.View) {
        super.attachView(view)
        view.showTimerList(timers)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }

    override fun provideTimers(timers: List<Timer>) {
        this.timers.clear()
        this.timers.addAll(timers)
        view?.showTimerList(this.timers)
    }

    override fun onLeftButtonClick() {
        if (selectedTimerPosition != null) {
            timersSectionEventListener.onEditTimerClick(timers[selectedTimerPosition!!])
        }
    }

    override fun onRightButtonClick() {
    }
}