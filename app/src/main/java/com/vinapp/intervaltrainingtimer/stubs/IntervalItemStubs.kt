package com.vinapp.intervaltrainingtimer.stubs

import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.ui_components.interval_item.IntervalItemData

val intervalItemDataStub = IntervalItemData(
    id = 0,
    name = "Interval",
    duration = "00:30",
    color = IntervalColor.RED
)

val intervalItemDataListStub = List(4) {
    intervalItemDataStub.copy(
        id = it,
        name = intervalItemDataStub.name + "_$it",
        color = IntervalColor.values()[it]
    )
}