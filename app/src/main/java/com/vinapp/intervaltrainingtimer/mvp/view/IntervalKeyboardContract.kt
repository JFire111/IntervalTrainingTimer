package com.vinapp.intervaltrainingtimer.mvp.view

import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter

interface IntervalKeyboardContract {

    interface View: MVPView {

        fun showTimeValue(timeValue: String, valuesArray: Array<Int?>)
    }

    abstract class Presenter: MVPPresenter<View>() {

        abstract fun onKeyboardButtonClick(number: Int)

        abstract fun onDeleteButtonClick()

        abstract fun onSaveButtonClick()

        abstract fun onCancelButtonClick()
    }
}