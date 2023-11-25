package com.vinapp.intervaltrainingtimer.mvp.view

import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter

interface IntervalKeyboardContract {

    interface View: MVPView {

        fun showDefaultIntervalName()

        fun showIntervalName(name: String)

        fun getIntervalName(): String

        fun showTimeValue(timeValue: String, valuesArray: Array<Int?>)

        fun showSelectedType(intervalColor: IntervalColor)
    }

    abstract class Presenter: MVPPresenter<View>() {

        abstract fun onKeyboardButtonClick(number: Int)

        abstract fun onDeleteButtonClick()

        abstract fun onRestButtonClick()

        abstract fun onWorkButtonClick()

        abstract fun onOkButtonClick()

        abstract fun onCancelButtonClick()
    }
}