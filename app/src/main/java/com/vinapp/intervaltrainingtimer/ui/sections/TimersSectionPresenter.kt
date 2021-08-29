package com.vinapp.intervaltrainingtimer.ui.sections

import android.util.Log
import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListOutput
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

class TimersSectionPresenter(override val sectionsEventHandler: SectionsEventHandler): TimerSectionContract.Presenter(), SideButtonsClickListener, TimerListOutput {

    var timers: List<Timer> = listOf()

    override fun onTimerItemClick(position: Int) {
    }

    override fun onAddTimerClick() {
        sectionsEventHandler.onAddTimerClick()
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
        Log.e("TimersSectionP", "onLeftButtonClick")
    }

    override fun onRightButtonClick() {
        Log.e("TimersSectionP", "onRightButtonClick")
    }

    override fun provideTimers(timers: List<Timer>) {
        this.timers = timers
        view?.showTimerList(timers)
    }
}