package com.vinapp.intervaltrainingtimer.mapping

import com.vinapp.intervaltrainingtimer.domain.Interval
import com.vinapp.intervaltrainingtimer.entities.IntervalEntity
import com.vinapp.intervaltrainingtimer.ui_components.interval_item.IntervalItemData
import com.vinapp.intervaltrainingtimer.utils.TimeConverter

object IntervalMapper {

    fun mapIntervalToIntervalEntity(interval: Interval): IntervalEntity {
        return IntervalEntity(
            id = interval.id,
            timerId = interval.timerId,
            name = interval.name,
            duration = interval.durationInSeconds,
            color = interval.color
        )
    }

    fun mapIntervalEntityToInterval(intervalEntity: IntervalEntity): Interval {
        return Interval(
            id = intervalEntity.id,
            timerId = intervalEntity.timerId,
            name = intervalEntity.name,
            durationInSeconds = intervalEntity.duration,
            color = intervalEntity.color
        )
    }

    fun mapIntervalToIntervalItemData(interval: Interval): IntervalItemData {
        return IntervalItemData(
            id = interval.id,
            name = interval.name,
            duration = TimeConverter.getTimeStringFromSeconds(interval.durationInSeconds),
            color = interval.color
        )
    }
}