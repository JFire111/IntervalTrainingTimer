package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.mvp.model.IntervalMVPModel
import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.SectionPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView

interface IntervalSectionContract {

    interface View: SectionView {

        fun showNumberOfRounds(numberOfRounds: Int)

        fun showIntervalList(intervalList: List<Interval>)
    }

    abstract class Presenter: SectionPresenter<View>() {

        abstract fun addRound()

        abstract fun removeRound()

        abstract fun onIntervalClick(position: Int)

        abstract fun onAddIntervalClick()

        abstract fun onDeleteIntervalClick()
    }
}