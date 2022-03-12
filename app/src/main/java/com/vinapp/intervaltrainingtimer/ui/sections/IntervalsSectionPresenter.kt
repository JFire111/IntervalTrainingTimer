package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingOutput
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.OnActionButtonsClickListener
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

class IntervalsSectionPresenter(override val intervalsSectionEventListener: IntervalsSectionEventListener): IntervalSectionContract.Presenter(), TimerEditingOutput, OnActionButtonsClickListener {

    private var timer: Timer? = null
    private var timerName: String? = null
    private var numberOfRounds: Int = 1
    private var intervals: ArrayList<Interval> = arrayListOf()

    override fun onNameChanged(name: String) {
        timerName = name
    }

    override fun addRound() {
        numberOfRounds++
        view!!.showNumberOfRounds(numberOfRounds)
    }

    override fun removeRound() {
        numberOfRounds--
        view!!.showNumberOfRounds(numberOfRounds)
    }

    override fun onIntervalClick(position: Int) {
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                intervals[position] = interval
            }

            override fun onCancel() {
            }
        }
        intervalsSectionEventListener.onIntervalClick(intervals[position], onIntervalKeyboardListener)
    }

    override fun onAddIntervalClick() {
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                intervals.add(interval)
            }

            override fun onCancel() {
            }
        }
        intervalsSectionEventListener.onAddIntervalClick(onIntervalKeyboardListener)
    }

    override fun onDeleteIntervalClick(position: Int) {
        intervals.removeAt(position)
        view!!.showIntervalList(intervals)
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

    override fun onStartButtonClick() {
        if (timer != null) {
            intervalsSectionEventListener.onStartTimerClick(timer!!)
        } else if (intervals.isNotEmpty()) {
            intervalsSectionEventListener.onStartTimerClick(Timer(null, timerName!!, numberOfRounds, intervals, null, null))
        }
    }

    override fun onLeftButtonClick() {
        intervalsSectionEventListener.onClearTimerClick()
    }

    override fun onRightButtonClick() {
        if (timer == null) {
            intervalsSectionEventListener.onSaveTimerClick(Timer(null, timerName!!, numberOfRounds, intervals, null, null))
        } else {
            intervalsSectionEventListener.onSaveTimerClick(timer!!)
        }
    }
}