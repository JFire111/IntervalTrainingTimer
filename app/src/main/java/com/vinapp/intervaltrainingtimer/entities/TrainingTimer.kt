package com.vinapp.intervaltrainingtimer.entities

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer

data class TrainingTimer(override val id: Int): Timer {
    override var name: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var intervals: List<Interval>
        get() = TODO("Not yet implemented")
        set(value) {}
}
