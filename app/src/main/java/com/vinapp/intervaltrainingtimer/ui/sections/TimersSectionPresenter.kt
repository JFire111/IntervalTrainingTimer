package com.vinapp.intervaltrainingtimer.ui.sections

import android.util.Log
import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

class TimersSectionPresenter(private val sectionsEventHandler: SectionsEventHandler): TimerSectionContract.Presenter() {

    override var repository: TimerMVPModel? = TimerRepository()

    override fun onTimerItemClick(position: Int) {
    }

    override fun onAddTimerClick() {
        sectionsEventHandler.onAddTimerClick()
    }

    override fun attachView(view: TimerSectionContract.View) {
        super.attachView(view)
        view.showTimerList(repository!!.getTimers())
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
        repository = null
    }

    override fun onLeftButtonClick() {
        Log.e("TimersSectionP", "onLeftButtonClick")
    }

    override fun onRightButtonClick() {
        Log.e("TimersSectionP", "onRightButtonClick")
    }
}