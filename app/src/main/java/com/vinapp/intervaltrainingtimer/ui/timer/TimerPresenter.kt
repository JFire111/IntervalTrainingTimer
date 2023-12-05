package com.vinapp.intervaltrainingtimer.ui.timer

import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.domain.Timer
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.TimerEntity
import com.vinapp.intervaltrainingtimer.logic.timer.TimerOutput
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.mvp.TimerContract
import com.vinapp.intervaltrainingtimer.services.TimerService
import com.vinapp.intervaltrainingtimer.services.TimerServiceController

class TimerPresenter(private val timer: Timer, private val serviceController: TimerServiceController): TimerContract.Presenter(), TimerOutput {

    private var delay: Long = 3000L
    private var remainingTime: Long = 0L // timer.getDurationInMillis()
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
            TimerState.INITIALIZED -> {
                serviceController.setDelay(delay)
                serviceController.start(timer)
            }
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

    override fun provideTime(time: Long) {
        remainingTime = time
        view!!.showTime(convertTimeToString(remainingTime))
    }

    override fun provideCurrentInterval(intervalIndex: Int) {
//        currentInterval = timer.duration[intervalIndex]
//        view!!.showMessage(timer.duration[intervalIndex].name)
//        view!!.setColorByIntervalType(timer.duration[intervalIndex].type)
//        view!!.setActionButtonIconByState(getActionButtonState())
    }

    private fun getActionButtonState(): TimerActionButtonState {
        var state: TimerActionButtonState = TimerActionButtonState.PLAY_WHITE
        if (currentInterval != null) {
            when (currentInterval!!.type) {
                IntervalColor.RED -> {
                    when (timerState) {
                        TimerState.IN_PROGRESS -> state = TimerActionButtonState.PAUSE_RED
                        TimerState.PAUSED -> state = TimerActionButtonState.PLAY_RED
                        TimerState.STOPPED -> TODO()
                        TimerState.FINISHED -> TODO()
                        TimerState.INITIALIZED -> TODO()
                    }
                }
                IntervalColor.GREEN -> {
                    when (timerState) {
                        TimerState.IN_PROGRESS -> state = TimerActionButtonState.PAUSE_GREEN
                        TimerState.PAUSED -> state = TimerActionButtonState.PLAY_GREEN
                        TimerState.STOPPED -> TODO()
                        TimerState.FINISHED -> TODO()
                        TimerState.INITIALIZED -> TODO()
                    }
                }
                IntervalColor.YELLOW -> TODO()
                IntervalColor.WHITE -> TODO()
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
}