package com.vinapp.intervaltrainingtimer.data.repositories

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.mvp.model.IntervalMVPModel

class IntervalRepository: IntervalMVPModel {

    private var intervalList: ArrayList<Interval> = arrayListOf()

    override fun addInterval(interval: Interval) {
        intervalList.add(interval)
    }

    override fun getIntervals(): ArrayList<Interval> {
        return intervalList
    }

    override fun updateInterval(position: Int, interval: Interval) {
        intervalList[position] = interval
    }

    override fun deleteInterval(position: Int) {
        intervalList.removeAt(position)
    }

    override fun clearIntervals() {
        intervalList.clear()
    }
}