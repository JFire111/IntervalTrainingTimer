package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.data.repositories.IntervalRepository
import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.SectionPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView

interface IntervalSectionContract {

    interface View: SectionView {

        fun showIntervalList(intervalList: ArrayList<Interval>)
    }

    abstract class Presenter: SectionPresenter<View>() {

        var intervalRepository: IntervalRepository? = null

        abstract fun onIntervalClick(position: Int)

        abstract fun onAddIntervalClick()

        abstract fun onDeleteIntervalClick()
    }
}