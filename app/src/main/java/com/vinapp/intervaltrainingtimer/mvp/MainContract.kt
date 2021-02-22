package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView

interface MainContract {

    interface View: MVPView {

        fun showSection(position: Int)

        fun showStartButton()

        fun hideStartButton()

        fun showLeftButton(text: String)

        fun hideLeftButton()

        fun showRightButton(text: String)

        fun hideRightButton()
    }

    abstract class Presenter: MVPPresenter<View>() {

        abstract fun onStartButtonClick()

        abstract fun onSaveButtonClick()

        abstract fun onEditButtonClick()

        abstract fun sectionSelected(section: Int)

        abstract fun sectionScrolled()
    }
}