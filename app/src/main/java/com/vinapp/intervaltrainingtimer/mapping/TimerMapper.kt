package com.vinapp.intervaltrainingtimer.mapping

import com.vinapp.intervaltrainingtimer.domain.Timer
import com.vinapp.intervaltrainingtimer.entities.IntervalEntity
import com.vinapp.intervaltrainingtimer.entities.TimerEntity
import com.vinapp.intervaltrainingtimer.mapping.IntervalMapper.mapIntervalEntityToInterval
import com.vinapp.intervaltrainingtimer.ui_components.timer_item.TimerItemData
import com.vinapp.intervaltrainingtimer.utils.TimeConverter

object TimerMapper {

    fun mapTimerToTimerEntity(timer: Timer): TimerEntity {
        return TimerEntity(
            id = timer.id,
            name = timer.name,
            numberOfRounds = timer.numberOfRounds,
            startDelay = timer.startDelay,
            timeBetweenRounds = timer.timeBetweenRounds,
            createdTime = timer.createdTime,
            updatedTime = timer.updatedTime
        )
    }

    fun mapTimerEntityToTimer(timerEntity: TimerEntity, intervalList: List<IntervalEntity>): Timer {
        return Timer(
            id = timerEntity.id,
            name = timerEntity.name,
            intervalList = intervalList.map(::mapIntervalEntityToInterval),
            numberOfRounds = timerEntity.numberOfRounds,
            startDelay = timerEntity.startDelay,
            timeBetweenRounds = timerEntity.timeBetweenRounds,
            createdTime = timerEntity.createdTime,
            updatedTime = timerEntity.updatedTime
        )
    }

    fun mapTimerToTimerItemData(timer: Timer): TimerItemData {
        return TimerItemData(
            id = timer.id,
            name = timer.name,
            duration = TimeConverter.getTimeString(
                numberOfRounds = timer.numberOfRounds,
                intervalList = timer.intervalList,
                timeBetweenRounds = timer.timeBetweenRounds
            ),
        )
    }
}