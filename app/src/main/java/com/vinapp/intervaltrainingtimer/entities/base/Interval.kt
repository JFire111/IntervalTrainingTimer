package com.vinapp.intervaltrainingtimer.entities.base

interface Interval {

    val name: String
    val duration: Int

    fun getDurationAsString(): String
}