package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel
import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.SectionPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView

interface TimerSectionContract {

    interface View: SectionView {

        fun showTimerList(timerList: ArrayList<Timer>)
    }

    abstract class Presenter: SectionPresenter<View>() {

        abstract val timerModel: TimerMVPModel?

        abstract fun onTimerItemClick(position: Int)

        abstract fun onAddTimerClick()
    }
}