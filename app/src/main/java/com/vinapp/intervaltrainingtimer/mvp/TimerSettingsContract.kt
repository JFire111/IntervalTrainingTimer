package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.SectionPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
import com.vinapp.intervaltrainingtimer.ui.Navigator

interface TimerSettingsContract {

    interface View: SectionView {

        fun showHintItem()

        fun showAddIntervalItem()

        fun showIntervalList()
    }

    abstract class Presenter(navigator: Navigator): SectionPresenter<View>(navigator) {

        abstract fun onAddIntervalClick()

        abstract fun onDeleteIntervalClick()
    }
}