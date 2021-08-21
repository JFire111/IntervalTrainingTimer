package com.vinapp.intervaltrainingtimer.mvp.model

import com.vinapp.intervaltrainingtimer.entities.base.Interval

interface IntervalMVPModel {

    fun addInterval(interval: Interval)

    fun getIntervals(): ArrayList<Interval>

    fun updateInterval(position: Int, interval: Interval)

    fun deleteInterval(position: Int)
}