package com.vinapp.intervaltrainingtimer.entities.base

interface Timer {

    val id: Int
    var name: String
    var intervals: List<Interval>

    fun getDuration(): Int

    fun getDurationAsString(): String
}