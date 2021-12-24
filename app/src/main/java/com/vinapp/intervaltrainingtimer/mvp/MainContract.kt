package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView
import com.vinapp.intervaltrainingtimer.services.TimerServiceController
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

interface MainContract {

    interface View: MVPView {

        fun showSection(position: Int)

        fun showTimerScreen(timer: Timer, serviceController: TimerServiceController)

        fun showIntervalKeyboard(interval: Interval?, onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener)

        fun hideIntervalKeyboard()

        fun showStartButton()

        fun hideStartButton()

        fun showLeftButton(text: String)

        fun hideLeftButton()

        fun showRightButton(text: String)

        fun hideRightButton()
    }

    abstract class Presenter: MVPPresenter<View>(), SectionsEventHandler {

        abstract fun onStartButtonClick()

        abstract fun sectionSelected(section: Int, sideButtonsClickListener: SideButtonsClickListener)

        abstract fun sectionScrolled()

        abstract fun onLeftButtonClick()

        abstract fun onRightButtonClick()
    }
}