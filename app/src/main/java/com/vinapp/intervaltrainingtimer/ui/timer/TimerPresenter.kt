package com.vinapp.intervaltrainingtimer.ui.timer

import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.logic.timer.TimerInput
import com.vinapp.intervaltrainingtimer.logic.timer.TimerOutput
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.mvp.TimerContract

class TimerPresenter(private val timerInput: TimerInput): TimerContract.Presenter(), TimerOutput {

    private var timerState: TimerState = TimerState.STOPPED
    private var currentInterval: Interval? = null

    override fun onTimerActionButtonClick() {
        when (timerState) {
            TimerState.STOPPED -> timerInput.start()
            TimerState.PAUSED -> timerInput.resume()
            TimerState.IN_PROGRESS -> timerInput.pause()
            //TimerState.FINISHED -> timerInput.restart()
        }
    }

    override fun destroy() {
    }

    override fun attachView(view: TimerContract.View) {
        super.attachView(view)
        timerInput.registerOutput(this)
    }

    override fun detachView() {
        super.detachView()
        timerInput.unregisterOutput()
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

    override fun provideTime(time: Long) {
        var timeInSeconds = Math.ceil(time / 1000.0).toInt()
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds - minutes * 60
        view!!.showTime("${minutes / 10}${minutes % 10}:${seconds / 10}${seconds%10}")
    }

    override fun provideCurrentInterval(interval: Interval) {
        currentInterval = interval
        view!!.showMessage(interval.name)
        view!!.setColorByIntervalType(interval.type)
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
}