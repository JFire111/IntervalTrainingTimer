package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.entities.TimerEntity
import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.SectionPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
//import com.vinapp.intervaltrainingtimer.ui.sections.TimersSectionEventListener

interface TimerSectionContract {

    interface View: SectionView {

        fun showTimerList(timerList: List<TimerEntity>)
    }

    abstract class Presenter: SectionPresenter<View>() {

//        abstract val timersSectionEventListener: TimersSectionEventListener

        abstract fun onTimerItemClick(position: Int)

        abstract fun onAddTimerClick()

        abstract fun onDeleteTimerClick(position: Int)
    }
}