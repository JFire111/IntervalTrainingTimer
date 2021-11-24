package com.vinapp.intervaltrainingtimer.ui.timer

import com.vinapp.intervaltrainingtimer.logic.timer.TimerInput
import com.vinapp.intervaltrainingtimer.mvp.TimerContract

class TimerPresenter(val timerInput: TimerInput): TimerContract.Presenter() {

    override fun onTimerActionButtonClick() {
    }

    override fun destroy() {
    }
}