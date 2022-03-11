package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingOutput
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

class IntervalsSectionPresenter(override val intervalsSectionEventListener: IntervalsSectionEventListener): IntervalSectionContract.Presenter(), TimerEditingOutput, SideButtonsClickListener {

    private var timerName: String? = null
    private var numberOfRounds: Int = 1
    private var intervals: ArrayList<Interval> = arrayListOf()

    override fun onNameChanged(name: String) {
        timerName = name
    }

    override fun addRound() {
        intervalsSectionEventListener.onAddRoundClick()
    }

    override fun removeRound() {
        intervalsSectionEventListener.onRemoveRoundClick()
    }

    override fun onIntervalClick(position: Int) {
        intervalsSectionEventListener.onIntervalClick(position)
    }

    override fun onAddIntervalClick() {
        intervalsSectionEventListener.onAddIntervalClick()
    }

    override fun onDeleteIntervalClick(position: Int) {
        intervalsSectionEventListener.onDeleteIntervalClick(position)
    }

    override fun attachView(view: IntervalSectionContract.View) {
        super.attachView(view)
        view.showTimerName(this.timerName)
        view.showIntervalList(this.intervals)
        view.showNumberOfRounds(this.numberOfRounds)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }

    override fun provideNumberOfRounds(numberOfRounds: Int) {
        this.numberOfRounds = numberOfRounds
        view?.showNumberOfRounds(this.numberOfRounds)
    }

    override fun provideIntervals(intervals: List<Interval>) {
        this.intervals.clear()
        this.intervals.addAll(intervals)
        view?.showIntervalList(this.intervals)
    }

    override fun provideTimer(timer: Timer) {
        intervalsSectionEventListener.setTimer(timer)
    }

    override fun onLeftButtonClick() {
        intervalsSectionEventListener.onClearTimerClick()
    }

    override fun onRightButtonClick() {
        intervalsSectionEventListener.onSaveTimerClick(timerName!!)
    }
}