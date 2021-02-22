package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.mvp.MainContract

class MainPresenter: MainContract.Presenter(), SectionsEventHandler {

    override fun onStartButtonClick() {
    }

    override fun onSaveButtonClick() {
        onSaveTimerClick()
    }

    override fun onEditButtonClick() {
    }

    override fun sectionSelected(section: Int) {
        when (section) {
            0 -> view!!.showRightButton("Save")
            1 -> view!!.hideRightButton()
        }
        view!!.showLeftButton("Edit")
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

    override fun onSaveTimerClick() {
        view!!.showSection(1)
    }

    override fun onAddTimerClick() {
        view!!.showSection(0)
    }

}