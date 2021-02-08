package com.vinapp.intervaltrainingtimer.ui.sections

import android.util.Log
import com.vinapp.intervaltrainingtimer.mvp.presenter.sections.TimerSettingsPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.TimerSettingsSectionView
import com.vinapp.intervaltrainingtimer.ui.Navigator

class TimerSettingsPresenter(navigator: Navigator): TimerSettingsPresenter(navigator) {

    override fun onAddIntervalClick() {
    }

    override fun onDeleteIntervalClick() {
    }

    override fun attachView(view: TimerSettingsSectionView) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }
}