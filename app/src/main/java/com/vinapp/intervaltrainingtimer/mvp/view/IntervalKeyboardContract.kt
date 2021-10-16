package com.vinapp.intervaltrainingtimer.mvp.view

import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter

interface IntervalKeyboardContract {

    interface View: MVPView {

        fun showIntervalName(name: String)

        fun showTimeValue(timeValue: String, valuesArray: Array<Int?>)

        fun showSelectedType(intervalType: IntervalType)
    }

    abstract class Presenter: MVPPresenter<View>() {

        abstract fun onKeyboardButtonClick(number: Int)

        abstract fun onDeleteButtonClick()

        abstract fun onRestButtonClick()

        abstract fun onWorkButtonClick()

        abstract fun onOkButtonClick(intervalName: String)

        abstract fun onCancelButtonClick()
    }
}