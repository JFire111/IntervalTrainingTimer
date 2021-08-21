package com.vinapp.intervaltrainingtimer.ui.sections

import android.util.Log
import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.logic.gettimers.GetTimersInteractor
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

class TimersSectionPresenter(
        override val timerRepository: TimerMVPModel,
        private val sectionsEventHandler: SectionsEventHandler): TimerSectionContract.Presenter(), SideButtonsClickListener {

    val getTimersInteractor = GetTimersInteractor(timerRepository)

    override fun onTimerItemClick(position: Int) {
    }

    override fun onAddTimerClick() {
        sectionsEventHandler.onAddTimerClick()
    }

    override fun attachView(view: TimerSectionContract.View) {
        super.attachView(view)
        view.showTimerList(getTimersInteractor.getTimersList())
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
}