package com.vinapp.intervaltrainingtimer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vinapp.intervaltrainingtimer.entities.IntervalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IntervalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterval(interval: IntervalEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInterval(interval: IntervalEntity)

    @Query("DELETE FROM interval WHERE timer_id == :timerId AND id == :intervalId")
    suspend fun deleteInterval(timerId: String, intervalId: String)

    @Query("SELECT * FROM interval WHERE timer_id == :timerId")
    suspend fun getIntervalListByTimerId(timerId: String): List<IntervalEntity>

    @Query("SELECT * FROM interval WHERE timer_id == :timerId")
    fun getIntervalListByTimerIdFlow(timerId: String): Flow<List<IntervalEntity>>

    @Query("SELECT id FROM interval WHERE timer_id == :timerId ORDER BY id DESC LIMIT 1")
    fun getLastIntervalId(timerId: String): Int
}