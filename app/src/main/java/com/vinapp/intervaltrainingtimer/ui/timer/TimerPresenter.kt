package com.vinapp.intervaltrainingtimer.ui.timer

import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.timer.TimerOutput
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.mvp.TimerContract
import com.vinapp.intervaltrainingtimer.services.TimerService
import com.vinapp.intervaltrainingtimer.services.TimerServiceController

class TimerPresenter(private val timer: Timer, private val serviceController: TimerServiceController): TimerContract.Presenter(), TimerOutput {

    private var delay: Long = 3000L
    private var timerProgress: Int = 0 // 0 is min, 100 is max
    private var intervalProgress: Int = 0 // 0 is min, 100 is max
    private var remainingTime: Long = timer.getDurationInMillis()
    private var remainingIntervalTime: Long? = null
    private var timerState: TimerState = TimerState.STOPPED
    private var currentInterval: Interval? = null

    init {
        serviceController.bindService(TimerService::class.java)
    }

    override fun changeDelay(value: Int) {
        delay = value * 1000L
        view!!.showDelay(value)
    }

    override fun onTimerActionButtonClick() {
        when (timerState) {
            TimerState.STOPPED -> {
                serviceController.setDelay(delay)
                serviceController.start(timer)
            }
            TimerState.PAUSED -> serviceController.resume()
            TimerState.IN_PROGRESS -> serviceController.pause()
            TimerState.FINISHED -> serviceController.restart()
        }
    }

    override fun destroy() {
        serviceController.unbindService()
        serviceController.stop()
    }

    override fun attachView(view: TimerContract.View) {
        super.attachView(view)
        view.showDelay((delay / 1000).toInt())
        view.showTime(convertTimeToString(remainingTime))
        serviceController.registerOutput(this)
    }

    override fun detachView() {
        super.detachView()
        serviceController.unregisterOutput()
    }

    override fun provideState(state: TimerState) {
        timerState = state
        if (state == TimerState.FINISHED) {
            currentInterval = null
            view!!.showMessage("Finish!")
            view!!.hideTime()
            view!!.setDefaultColor()
        }
        view!!.setActionButtonIconByState(getActionButtonState())
    }

    override fun provideDelay(delay: Long) {
        this.delay = delay
        if (delay > 0) {
            view!!.showDelay((this.delay / 1000).toInt())
        } else {
            view!!.hideDelaySeekBar()
        }
    }

    override fun provideTime(remainingTime: Long, remainingIntervalTime: Long) {
        this.remainingTime = remainingTime
        this.remainingIntervalTime = remainingIntervalTime
        view!!.showTime(convertTimeToString(this.remainingTime))
        this.timerProgress = computeProgressInPercent(timer.getDurationInMillis(), this.remainingTime)
        currentInterval?.let {
            this.intervalProgress = computeProgressInPercent(currentInterval!!.getDurationInMillis(), this.remainingIntervalTime!!)
        }
        view!!.showProgress(this.timerProgress, this.intervalProgress)
    }

    override fun provideCurrentInterval(intervalIndex: Int) {
        currentInterval = timer.intervals[intervalIndex]
        view!!.showMessage(timer.intervals[intervalIndex].name)
        view!!.setColorByIntervalType(timer.intervals[intervalIndex].type)
        view!!.setActionButtonIconByState(getActionButtonState())
    }

    private fun getActionButtonState(): TimerActionButtonState {
        var state: TimerActionButtonState = TimerActionButtonState.PLAY_WHITE
        if (currentInterval != null) {
            when (currentInterval!!.type) {
                IntervalType.WORK -> {
                    when (timerState) {
                        TimerState.IN_PROGRESS -> state = TimerActionButtonState.PAUSE_RED
                        TimerState.PAUSED -> state = TimerActionButtonState.PLAY_RED
                    }
                }
                IntervalType.REST -> {
                    when (timerState) {
                        TimerState.IN_PROGRESS -> state = TimerActionButtonState.PAUSE_GREEN
                        TimerState.PAUSED -> state = TimerActionButtonState.PLAY_GREEN
                    }
                }
            }
        }
        return state
    }

    private fun convertTimeToString(time: Long): String {
        val timeInSeconds = Math.ceil(time / 1000.0).toInt()
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds - minutes * 60
        return "${minutes / 10}${minutes % 10}:${seconds / 10}${seconds%10}"
    }

    private fun computeProgressInPercent(fullValue: Long, currentValue: Long): Int {
        val progress = 100 - (((currentValue) * 100) / fullValue).toInt()
        return if (progress <= 100) {
            progress
        } else {
            100
        }
    }
}