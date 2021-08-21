package com.vinapp.intervaltrainingtimer.ui.sections

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingInteractor
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.mvp.model.IntervalMVPModel
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

class IntervalsSectionPresenter(
        override val sectionsEventHandler: SectionsEventHandler,
        override val intervalRepository: IntervalMVPModel,
        timerRepository: TimerMVPModel): IntervalSectionContract.Presenter(), SideButtonsClickListener {

    private val timerEditingInteractor = TimerEditingInteractor(timerRepository)

    override fun onIntervalClick(position: Int) {
        val currentInterval = intervalRepository.getIntervals()[position]
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                intervalRepository.getIntervals()[position] = interval
            }

            override fun onCancel() {
            }
        }
        sectionsEventHandler.onIntervalClick(currentInterval, onIntervalKeyboardListener)
    }

    override fun onAddIntervalClick() {
        val onIntervalKeyboardListener = object : SectionsEventHandler.OnIntervalKeyboardListener {
            override fun onSave(interval: Interval) {
                intervalRepository.addInterval(interval)
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
        view.showIntervalList(intervalRepository.getIntervals())
    }

    override fun detachView() {
        super.detachView()
    }

    override fun destroy() {
    }

    override fun onLeftButtonClick() {
        this.intervalRepository.clearIntervals()
        view!!.showIntervalList(intervalRepository.getIntervals())
    }

    override fun onRightButtonClick() {
        timerEditingInteractor.createTimer(intervalRepository.getIntervals())
        sectionsEventHandler.onSaveTimerClick()
        this.intervalRepository.clearIntervals()
        view!!.showIntervalList(intervalRepository.getIntervals())
    }
}