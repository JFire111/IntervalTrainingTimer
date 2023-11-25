package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.TimerEntity
import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView
import com.vinapp.intervaltrainingtimer.services.TimerServiceController
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.OnActionButtonsClickListener

interface MainContract {

    interface View: MVPView {

        fun showSection(position: Int)

        fun showTimerScreen(timer: TimerEntity, serviceController: TimerServiceController)

        fun showIntervalKeyboard(interval: Interval?, onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener)

        fun hideIntervalKeyboard()

        fun showStartButton()

        fun hideStartButton()

        fun showClearButton()

        fun hideClearButton()

        fun showSaveButton()

        fun hideSaveButton()

        fun showEditButton()

        fun hideEditButton()
    }

    abstract class Presenter: MVPPresenter<View>(), SectionsEventHandler {

        abstract fun sectionSelected(section: Int, onActionButtonsClickListener: OnActionButtonsClickListener)

        abstract fun sectionScrolled()

    }
}