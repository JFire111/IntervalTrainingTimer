package com.vinapp.intervaltrainingtimer.entities

import com.vinapp.intervaltrainingtimer.common.IntervalType
import kotlinx.serialization.Serializable

@Serializable
data class Interval(val name: String, val duration: Int, val type: IntervalType) {

    fun getDurationAsString(): String {
        val minutes = duration / 60
        val seconds = duration - minutes * 60
        return "${minutes / 10}${minutes % 10}:${seconds / 10}${seconds%10}"
    }
}