package com.vinapp.intervaltrainingtimer.entities

import com.vinapp.intervaltrainingtimer.entities.base.BaseInterval

data class WorkInterval(override val name: String, override val duration: Int): BaseInterval()
