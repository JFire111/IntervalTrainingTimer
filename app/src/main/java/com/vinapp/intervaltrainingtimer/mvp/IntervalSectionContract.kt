package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.SectionPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
import com.vinapp.intervaltrainingtimer.ui.sections.IntervalsSectionEventListener

interface IntervalSectionContract {

    interface View: SectionView {

        fun showTimerName(name: String?)

        fun getTimerName(): String

        fun showNumberOfRounds(numberOfRounds: Int)

        fun showIntervalList(intervalList: List<Interval>)
    }

    abstract class Presenter: SectionPresenter<View>() {

        abstract val intervalsSectionEventListener: IntervalsSectionEventListener

        abstract fun addRound()

        abstract fun removeRound()

        abstract fun onIntervalClick(position: Int)

        abstract fun onAddIntervalClick()

        abstract fun onDeleteIntervalClick(position: Int)
    }
}