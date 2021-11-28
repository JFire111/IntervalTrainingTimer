package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView
import com.vinapp.intervaltrainingtimer.ui.timer.TimerActionButtonState

interface TimerContract {

    interface View: MVPView {

        fun showMessage(message: String)

        fun showTime(time: String)

        fun hideTime()

        fun setColorByIntervalType(type: IntervalType)

        fun setDefaultColor()

        fun setActionButtonIconByState(state: TimerActionButtonState)
    }

    abstract class Presenter: MVPPresenter<View>() {

        abstract fun onTimerActionButtonClick()
    }
}