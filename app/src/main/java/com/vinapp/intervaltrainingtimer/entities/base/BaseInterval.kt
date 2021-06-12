package com.vinapp.intervaltrainingtimer.entities.base

abstract class BaseInterval: Interval {

    override fun getDurationAsString(): String {
        val minutes = duration / 60
        val seconds = duration - minutes * 60
        return "${minutes / 10}${minutes % 10}:${seconds / 10}${seconds%10}"
    }
}