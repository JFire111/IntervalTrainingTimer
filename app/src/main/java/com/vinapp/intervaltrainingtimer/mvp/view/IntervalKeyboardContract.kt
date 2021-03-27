package com.vinapp.intervaltrainingtimer.mvp.view

import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter

interface IntervalKeyboardContract {

    interface View: MVPView

    abstract class Presenter: MVPPresenter<View>() {

        abstract fun onSaveButtonClick(duration: Int)

        abstract fun onCancelButtonClick()
    }
}