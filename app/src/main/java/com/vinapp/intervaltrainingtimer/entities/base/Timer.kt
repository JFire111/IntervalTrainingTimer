package com.vinapp.intervaltrainingtimer.entities.base

interface Timer {

    val id: Int
    var name: String
    var numberOfRounds: Int
    var intervals: List<Interval>

    fun getDuration(): Int

    fun getDurationAsString(): String
}