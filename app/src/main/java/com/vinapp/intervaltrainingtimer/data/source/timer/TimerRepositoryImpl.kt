package com.vinapp.intervaltrainingtimer.data.source.timer

import com.vinapp.intervaltrainingtimer.data.database.IntervalDao
import com.vinapp.intervaltrainingtimer.data.database.TimerDao
import com.vinapp.intervaltrainingtimer.domain.entities.Timer
import com.vinapp.intervaltrainingtimer.mapping.TimerMapper.mapTimerEntityToTimer
import com.vinapp.intervaltrainingtimer.mapping.TimerMapper.mapTimerToTimerEntity
import com.vinapp.intervaltrainingtimer.mapping.IntervalMapper.mapIntervalToIntervalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TimerRepositoryImpl(
    private val timerDao: TimerDao,
    private val intervalDao: IntervalDao,
) : TimerRepository {

    override suspend fun saveTimer(timer: Timer) {
        timerDao.insert(mapTimerToTimerEntity(timer))
        intervalDao.insertIntervalList(timer.intervalList.map(::mapIntervalToIntervalEntity))
    }

    override fun getTimerListFlow(): Flow<List<Timer>> {
        return timerDao.getAllTimersFlow().map {
            it.map { timerEntity ->
                mapTimerEntityToTimer(
                    timerEntity = timerEntity,
                    intervalList = intervalDao.getIntervalListByTimerId(timerEntity.id)
                )
            }
        }
    }

    override suspend fun getTimerById(id: String): Timer? {
        return timerDao.getTimerById(id)?.let { timerEntity ->
            mapTimerEntityToTimer(
                timerEntity = timerEntity,
                intervalList = intervalDao.getIntervalListByTimerId(timerEntity.id)
            )
        }
    }

    override suspend fun getTimerByIdFlow(id: String): Flow<Timer?> {
        return timerDao.getTimerByIdFlow(id).map {
            it?.let { timerEntity ->
                mapTimerEntityToTimer(
                    timerEntity = timerEntity,
                    intervalList = intervalDao.getIntervalListByTimerId(timerEntity.id)
                )
            }
        }
    }

    override suspend fun deleteTimer(timerId: String) {
        timerDao.delete(timerId)
    }
}