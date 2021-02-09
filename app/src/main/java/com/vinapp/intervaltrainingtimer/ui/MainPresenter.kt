package com.vinapp.intervaltrainingtimer.ui

import com.vinapp.intervaltrainingtimer.mvp.MainContract

class MainPresenter: MainContract.Presenter(), SectionsNavigator {

    override fun onStartTimerClick() {
    }

    override fun onSaveTimerClick() {
        setSection(1)
    }

    override fun onDeleteTimerClick() {
    }

    override fun detachView() {
    }

    override fun destroy() {
    }

    override fun setSection(position: Int) {
        view!!.showSection(position)
    }

}