package com.vinapp.intervaltrainingtimer.entities

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer

data class TrainingTimer(val id: Int, override var name: String, override var intervals: List<Interval>): Timer {

}
