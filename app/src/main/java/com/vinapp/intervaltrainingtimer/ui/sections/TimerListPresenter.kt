package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.mvp.TimerListContract
import com.vinapp.intervaltrainingtimer.mvp.model.TimerModel
import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.TimerListPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.TimerListSectionView
import com.vinapp.intervaltrainingtimer.ui.Navigator

class TimerListPresenter(navigator: Navigator): TimerListContract.Presenter(navigator) {

    override val timerModel: TimerModel = TimerRepository()

    override fun onTimerItemClick(position: Int) {
    }

    override fun onAddTimerClick() {
        //Change section to TimerSettingsSection in MainView
        //Use Interactor (?)
        navigator.setSection(0)
    }

    override fun attachView(view: TimerListContract.View) {
        super.attachView(view)
        view.showTimerList(timerModel.getTimers())
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }
}