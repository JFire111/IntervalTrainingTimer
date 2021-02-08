package com.vinapp.intervaltrainingtimer.mvp

import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.SectionPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView

interface TimerSettingsContract {

    interface View: SectionView {

        fun showHintItem()

        fun showAddIntervalItem()

        fun showIntervalList()
    }

    abstract class Presenter: SectionPresenter<View>() {

        abstract fun onAddIntervalClick()

        abstract fun onDeleteIntervalClick()
    }
}