package com.vinapp.intervaltrainingtimer.data.database

import androidx.room.*
import com.vinapp.intervaltrainingtimer.entities.TimerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timer: TimerEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(timer: TimerEntity)

    @Query("DELETE FROM timer WHERE id = :timerId")
    fun delete(timerId: String)

    @Query("SELECT * FROM timer ORDER BY created_time")
    fun getAllTimersFlow(): Flow<List<TimerEntity>>

    @Query("SELECT * FROM timer WHERE id = :id")
    fun getTimerById(id: String): TimerEntity?

    @Query("SELECT * FROM timer WHERE id = :id")
    fun getTimerByIdFlow(id: String): Flow<TimerEntity?>
}