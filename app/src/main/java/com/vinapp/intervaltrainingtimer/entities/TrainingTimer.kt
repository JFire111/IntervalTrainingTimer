package com.vinapp.intervaltrainingtimer.entities

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer

data class TrainingTimer(val id: Int): Timer {
    override var name: String
        get() = TODO("Not yet implemented")
        set(value) {}

}
