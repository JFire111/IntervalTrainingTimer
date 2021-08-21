package com.vinapp.intervaltrainingtimer.ui.sections

import android.util.Log
import com.vinapp.intervaltrainingtimer.data.repositories.IntervalRepository
import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler

class IntervalsSectionPresenter(
        private val sectionsEventHandler: SectionsEventHandler): IntervalSectionContract.Presenter() {

    override fun onIntervalClick(position: Int) {
        val currentInterval = intervalRepository!!.getIntervals()[position]
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                intervalRepository!!.getIntervals()[position] = interval
            }

            override fun onCancel() {
            }
        }
        sectionsEventHandler.onIntervalClick(currentInterval, onIntervalKeyboardListener)
    }

    override fun onAddIntervalClick() {
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                intervalRepository!!.addInterval(interval)
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
        view.showIntervalList(intervalRepository!!.getIntervals())
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }

    override fun onLeftButtonClick() {
        Log.e("IntervalsSectionP", "onLeftButtonClick")
    }

    override fun onRightButtonClick() {
        Log.e("IntervalsSectionP", "onRightButtonClick")
    }
}