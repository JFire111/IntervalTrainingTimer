package com.vinapp.intervaltrainingtimer.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class TimerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "number_of_rounds")
    val numberOfRounds: Int,
    @ColumnInfo(name = "start_delay")
    val startDelay: Int,
    @ColumnInfo(name = "time_between_rounds")
    val timeBetweenRounds: Int,
    @ColumnInfo(name = "created_time")
    val createdTime: Long,
    @ColumnInfo(name = "updated_time")
    val updatedTime: Long?)