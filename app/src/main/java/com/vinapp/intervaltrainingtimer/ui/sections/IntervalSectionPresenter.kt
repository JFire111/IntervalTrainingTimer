package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

class IntervalSectionPresenter(private val sectionsEventHandler: SectionsEventHandler): IntervalSectionContract.Presenter() {

    override fun onIntervalClick() {
    }

    override fun onAddIntervalClick() {
    }

    override fun onDeleteIntervalClick() {
    }

    override fun attachView(view: IntervalSectionContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }
}