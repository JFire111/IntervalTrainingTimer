package com.vinapp.intervaltrainingtimer.domain.entities

data class Timer(
    val id: String,
    val name: String,
    val intervalList: List<Interval>,
    val numberOfRounds: Int,
    val startDelay: Int,
    val timeBetweenRounds: Int,
    val createdTime: Long,
    val updatedTime: Long?,
)
