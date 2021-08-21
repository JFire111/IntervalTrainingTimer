package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.mvp.MainContract

class MainPresenter(override var currentSection: Int) : MainContract.Presenter() {

    var sideButtonsClickListener: SideButtonsClickListener? = null

    override fun onStartButtonClick() {
    }

    override fun sectionSelected(section: Int, sideButtonsClickListener: SideButtonsClickListener) {
        this.currentSection = section
        this.sideButtonsClickListener = sideButtonsClickListener
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

    override fun onLeftButtonClick() {
        sideButtonsClickListener?.onLeftButtonClick()
    }

    override fun onRightButtonClick() {
        sideButtonsClickListener?.onRightButtonClick()
    }

    override fun detachView() {
    }

    override fun destroy() {
    }

    override fun onIntervalClick(interval: Interval, onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener) {
        view!!.showIntervalKeyboard(interval, onIntervalKeyboardListener)
    }

    override fun onAddIntervalClick(onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener) {
        view!!.showIntervalKeyboard(null, onIntervalKeyboardListener)
    }

    override fun onCloseIntervalKeyboard() {
        view!!.hideIntervalKeyboard()
    }

    override fun onAddTimerClick() {
        view!!.showSection(0)
    }

}