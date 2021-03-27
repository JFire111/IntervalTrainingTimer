package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.MainContract

class MainPresenter: MainContract.Presenter() {

    override fun onStartButtonClick() {
    }

    override fun onSaveButtonClick() {
        onSaveTimerClick()
    }

    override fun onEditButtonClick() {
    }

    override fun sectionSelected(section: Int) {
        when (section) {
            0 -> {
                view!!.showLeftButton("Clear")
                view!!.showRightButton("Save")
            }
            1 -> {
                view!!.showLeftButton("Edit")
                view!!.hideRightButton()
            }
        }
        view!!.showStartButton()
    }

    override fun sectionScrolled() {
        view!!.hideLeftButton()
        view!!.hideRightButton()
        view!!.hideStartButton()
    }

    override fun detachView() {
    }

    override fun destroy() {
    }

    override fun onAddIntervalClick(onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener) {
        view!!.showIntervalKeyboard(onIntervalKeyboardListener)
    }

    override fun onCloseIntervalKeyboard() {
        view!!.hideIntervalKeyboard()
    }

    override fun onAddTimerClick() {
        view!!.showSection(0)
    }

    override fun onSaveTimerClick() {
        view!!.showSection(1)
    }

}