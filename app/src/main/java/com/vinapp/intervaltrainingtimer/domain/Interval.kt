package com.vinapp.intervaltrainingtimer.domain

import com.vinapp.intervaltrainingtimer.common.IntervalColor

data class Interval(
    val id: Int,
    val timerId: String,
    val name: String,
    val duration: Long,
    val color: IntervalColor
)
