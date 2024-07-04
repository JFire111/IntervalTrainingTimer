package com.vinapp.intervaltrainingtimer.stubs

import com.vinapp.intervaltrainingtimer.ui_components.timer_item.TimerItemData

val timerItemDataStub = TimerItemData(
    id = "",
    name = "Timer",
    duration = "10:00"
)

val timerItemDataListStub = List(5) {
    timerItemDataStub.copy(
        id = it.toString(),
        name = timerItemDataStub.name + "_$it"
    )
}