package com.vinapp.intervaltrainingtimer.entities

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer

data class TrainingTimer(override val id: Int, override var name: String, override var numberOfRounds: Int, override var intervals: List<Interval>): Timer {

    override fun getDuration(): Int {
        var duration = 0
        intervals.forEach{
            duration += it.duration
        }
        return duration
    }

    override fun getDurationAsString(): String {
        val minutes = getDuration() / 60
        val seconds = getDuration() - minutes * 60
        return "${minutes / 10}${minutes % 10}:${seconds / 10}${seconds%10}"
    }

}
