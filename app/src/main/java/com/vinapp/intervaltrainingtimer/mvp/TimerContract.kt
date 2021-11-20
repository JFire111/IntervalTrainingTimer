package com.vinapp.intervaltrainingtimer.mvp

import android.graphics.Color
import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView

interface TimerContract {

    interface View: MVPView {

        fun showMessage(message: String)

        fun showTime(long: Long)

        fun setColor(color: Color)
    }

    abstract class Presenter: MVPPresenter<View>() {
    }
}