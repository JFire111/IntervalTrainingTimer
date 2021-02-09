package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.mvp.TimerListContract
import com.vinapp.intervaltrainingtimer.mvp.model.TimerModel
import com.vinapp.intervaltrainingtimer.ui.SectionsNavigator

class TimerListPresenter(val sectionNavigator: SectionsNavigator): TimerListContract.Presenter() {

    override var timerModel: TimerModel? = TimerRepository()

    override fun onTimerItemClick(position: Int) {
    }

    override fun onAddTimerClick() {
        sectionNavigator.setSection(0)
    }

    override fun attachView(view: TimerListContract.View) {
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