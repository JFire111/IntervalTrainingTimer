package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingInteractor
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingOutput
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.mvp.model.IntervalMVPModel
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

class IntervalsSectionPresenter(override val sectionsEventHandler: SectionsEventHandler): IntervalSectionContract.Presenter(), SideButtonsClickListener, TimerEditingOutput {

    private var intervals: ArrayList<Interval> = arrayListOf()

    override fun onIntervalClick(position: Int) {
        sectionsEventHandler.onIntervalClick(position)
    }

    override fun onAddIntervalClick() {
        sectionsEventHandler.onAddIntervalClick()
    }

    override fun onDeleteIntervalClick() {
    }

    override fun attachView(view: IntervalSectionContract.View) {
        super.attachView(view)
        view.showIntervalList(this.intervals)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }

    override fun onLeftButtonClick() {
        /*this.intervalRepository.clearIntervals()
        view!!.showIntervalList(intervalRepository.getIntervals())*/
    }

    override fun onRightButtonClick() {
        sectionsEventHandler.onSaveTimerClick()
    }

    override fun provideIntervals(intervals: List<Interval>) {
        this.intervals.clear()
        this.intervals.addAll(intervals)
        view?.showIntervalList(this.intervals)
    }
}