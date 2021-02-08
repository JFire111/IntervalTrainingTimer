package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerModel
import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.SectionPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
import com.vinapp.intervaltrainingtimer.ui.Navigator

interface TimerListContract {

    interface View: SectionView {

        fun showTimerList(timerList: List<Timer>)
    }

    abstract class Presenter(navigator: Navigator): SectionPresenter<View>(navigator) {

        abstract val timerModel: TimerModel

        abstract fun onTimerItemClick(position: Int)

        abstract fun onAddTimerClick()
    }
}