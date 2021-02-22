package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import com.vinapp.intervaltrainingtimer.mvp.model.TimerModel
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

class TimerSectionPresenter(private val sectionsEventHandler: SectionsEventHandler): TimerSectionContract.Presenter() {

    override var timerModel: TimerModel? = TimerRepository()

    override fun onTimerItemClick(position: Int) {
    }

    override fun onAddTimerClick() {
        sectionsEventHandler.onAddTimerClick()
    }

    override fun attachView(view: TimerSectionContract.View) {
        super.attachView(view)
        view.showTimerList(timerModel!!.getTimers())
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
        timerModel = null
    }
}