package com.vinapp.intervaltrainingtimer.ui.sections

import android.util.Log
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListOutput
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

class TimersSectionPresenter(override val sectionsEventHandler: SectionsEventHandler): TimerSectionContract.Presenter(), SideButtonsClickListener, TimerListOutput {

    var selectedTimerPosition: Int? = null
    var timers: ArrayList<Timer> = arrayListOf()

    override fun onTimerItemClick(position: Int) {
        selectedTimerPosition = position
        sectionsEventHandler.onTimerClick(position)
    }

    override fun onAddTimerClick() {
        sectionsEventHandler.onAddTimerClick()
    }

    override fun onDeleteTimerClick(position: Int) {
        sectionsEventHandler.onDeleteTimerClick(timers[position].id)
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

    override fun onLeftButtonClick() {
        if (selectedTimerPosition != null) {
            sectionsEventHandler.onEditTimerClick(timers[selectedTimerPosition!!])
        }
    }

    override fun onRightButtonClick() {
        Log.e("TimersSectionP", "onRightButtonClick")
    }

    override fun provideTimers(timers: List<Timer>) {
        this.timers.clear()
        this.timers.addAll(timers)
        view?.showTimerList(this.timers)
    }
}