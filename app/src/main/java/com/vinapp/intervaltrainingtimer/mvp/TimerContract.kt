package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView

interface TimerContract {

    interface View: MVPView {

        fun showMessage(message: String)

        fun showTime(time: Long)

        fun setColorByIntervalType(type: IntervalType)
    }

    abstract class Presenter: MVPPresenter<View>() {

        abstract fun onTimerActionButtonClick()
    }
}