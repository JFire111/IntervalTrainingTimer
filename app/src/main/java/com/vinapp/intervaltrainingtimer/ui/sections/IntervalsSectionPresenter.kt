package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.timer_editing.TimerEditingOutput
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.OnActionButtonsClickListener
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

class IntervalsSectionPresenter(override val intervalsSectionEventListener: IntervalsSectionEventListener): IntervalSectionContract.Presenter(), TimerEditingOutput, OnActionButtonsClickListener {

    private var timer: Timer? = null
    private var timerName: String? = null
    private var numberOfRounds: Int = 1
    private var intervals: ArrayList<Interval> = arrayListOf()

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
        updateView(view)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }

    override fun provideTimer(timer: Timer) {
        this.timer = timer
        this.timerName = timer.name
        this.numberOfRounds = timer.numberOfRounds
        this.intervals.clear()
        this.intervals.addAll(timer.intervals)
        updateView(view!!)
    }

    override fun onStartButtonClick() {
        if (timer == null) {
            timer = getNewTimer()
        } else {
            timer!!.name = timerName!!
            timer!!.numberOfRounds = numberOfRounds
            timer!!.intervals = intervals
        }
        if (timer!!.intervals.isNotEmpty()) {
            intervalsSectionEventListener.onStartTimerClick(timer!!)
        }
    }

    override fun onLeftButtonClick() {
        intervalsSectionEventListener.onClearTimerClick()
    }

    override fun onRightButtonClick() {
        timerName = view!!.getTimerName()
        if (timer == null) {
            intervalsSectionEventListener.onSaveTimerClick(getNewTimer())
        } else {
            intervalsSectionEventListener.onSaveTimerClick(timer!!.copy(name = timerName!!, numberOfRounds = numberOfRounds, intervals = intervals))
        }
    }

    private fun getNewTimer(): Timer {
        timerName = view!!.getTimerName()
        return Timer(null, timerName!!, numberOfRounds, intervals, null, null)
    }

    private fun updateView(view: IntervalSectionContract.View) {
        view.showTimerName(timerName)
        view.showIntervalList(intervals)
        view.showNumberOfRounds(numberOfRounds)
    }
}