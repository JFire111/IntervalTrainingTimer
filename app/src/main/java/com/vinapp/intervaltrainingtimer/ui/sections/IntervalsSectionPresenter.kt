package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

class IntervalsSectionPresenter(private val sectionsEventHandler: SectionsEventHandler): IntervalSectionContract.Presenter() {

    var intervalList: ArrayList<Interval> = ArrayList()

    override fun onIntervalClick() {
    }

    override fun onAddIntervalClick() {
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                intervalList.add(interval)
            }

            override fun onCancel() {
            }
        }
        sectionsEventHandler.onAddIntervalClick(onIntervalKeyboardListener)
    }

    override fun onDeleteIntervalClick() {
    }

    override fun attachView(view: IntervalSectionContract.View) {
        super.attachView(view)
        view.showIntervalList(intervalList)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }
}