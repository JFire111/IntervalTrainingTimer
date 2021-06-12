package com.vinapp.intervaltrainingtimer.entities

import com.vinapp.intervaltrainingtimer.entities.base.Interval

data class WorkInterval(override val name: String, override val duration: Int): Interval
