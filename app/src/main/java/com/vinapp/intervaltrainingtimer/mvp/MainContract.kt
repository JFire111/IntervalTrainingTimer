package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView

interface MainContract {

    interface View: MVPView {

        fun showSection(position: Int)

        fun showStartButton()

        fun showSaveButton()

        fun showDeleteButton()

    }

    abstract class Presenter: MVPPresenter<View>() {

        abstract fun onStartTimerClick()

        abstract fun onSaveTimerClick()

        abstract fun onDeleteTimerClick()
    }
}