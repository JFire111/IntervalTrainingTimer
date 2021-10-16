package com.vinapp.intervaltrainingtimer.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Timer(
        @PrimaryKey(autoGenerate = true) val id: Int,
        var name: String,
        var numberOfRounds: Int,
        var intervals: List<Interval>,
        val createdTime: Long,
        var updatedTime: Long?) {

    fun getDuration(): Int {
        var duration = 0
        intervals.forEach{
            duration += it.duration
        }
        duration *= numberOfRounds
        return duration
    }

    fun getDurationAsString(): String {
        val minutes = getDuration() / 60
        val seconds = getDuration() - minutes * 60
        return "${minutes / 10}${minutes % 10}:${seconds / 10}${seconds%10}"
    }

}
