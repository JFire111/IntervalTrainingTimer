package com.vinapp.intervaltrainingtimer.data.interval

import com.vinapp.intervaltrainingtimer.data.database.IntervalDao
import com.vinapp.intervaltrainingtimer.domain.Interval
import com.vinapp.intervaltrainingtimer.mapping.IntervalMapper.mapIntervalEntityToInterval
import com.vinapp.intervaltrainingtimer.mapping.IntervalMapper.mapIntervalToIntervalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IntervalRepositoryImpl(
    private val intervalDao: IntervalDao
) : IntervalRepository {

    override suspend fun saveInterval(interval: Interval) {
        intervalDao.insertInterval(mapIntervalToIntervalEntity(interval))
    }

    override suspend fun updateInterval(interval: Interval) {
        intervalDao.updateInterval(mapIntervalToIntervalEntity(interval))
    }

    override suspend fun deleteInterval(timerId: String, intervalId: String) {
        intervalDao.deleteInterval(
            timerId = timerId,
            intervalId = intervalId
        )
    }

    override fun getIntervalListFlow(timerId: String): Flow<List<Interval>> {
        return intervalDao.getIntervalListByTimerIdFlow(timerId).map {
            it.map(::mapIntervalEntityToInterval)
        }
    }
}