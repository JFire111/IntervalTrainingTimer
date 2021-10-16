package com.vinapp.intervaltrainingtimer.data.database

import androidx.room.*
import com.vinapp.intervaltrainingtimer.entities.Timer

@Dao
interface TimerDao {

    @Insert
    fun insert(timer: Timer)

    @Update
    fun update(timer: Timer)

    @Query("DELETE FROM timer WHERE id = :timerId")
    fun delete(timerId: Int)

    @Query("SELECT * FROM timer ORDER BY createdTime")
    fun getAll(): List<Timer>

    @Query("SELECT * FROM timer WHERE id = :id")
    fun getById(id: Int): Timer?
}