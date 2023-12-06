package com.vinapp.intervaltrainingtimer.data.source.timer

import com.vinapp.intervaltrainingtimer.base.data.Repository
import com.vinapp.intervaltrainingtimer.domain.entities.Timer
import kotlinx.coroutines.flow.Flow

interface TimerRepository : Repository {
    suspend fun saveTimer(timer: Timer)
    fun getTimerListFlow(): Flow<List<Timer>>
    suspend fun getTimerById(id: String): Timer?
    suspend fun getTimerByIdFlow(id: String): Flow<Timer?>
    suspend fun deleteTimer(timerId: String)
}