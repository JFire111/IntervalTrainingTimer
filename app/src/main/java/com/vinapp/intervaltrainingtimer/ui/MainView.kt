package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.mvp.view.MVPView

interface MainView: MVPView {

    fun selectTab()

    fun openTimer()

    fun openSettings()
}