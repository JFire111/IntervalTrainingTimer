package com.vinapp.intervaltrainingtimer.entities

import com.vinapp.intervaltrainingtimer.common.IntervalColor

data class Interval(val name: String, val duration: Int, val type: IntervalColor) {

    fun getDurationInMillis(): Long {
        return (duration * 1000).toLong()
    }

    fun getDurationAsString(): String {
        val minutes = duration / 60
        val seconds = duration - minutes * 60
        return "${minutes / 10}${minutes % 10}:${seconds / 10}${seconds%10}"
    }
}