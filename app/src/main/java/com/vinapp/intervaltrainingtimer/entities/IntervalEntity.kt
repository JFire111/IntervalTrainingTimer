package com.vinapp.intervaltrainingtimer.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.data.database.converters.IntervalColorConverter

@Entity(
    tableName = "interval",
    primaryKeys = ["id", "timer_id"]
)
data class IntervalEntity(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "timer_id")
    val timerId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "duration")
    val duration: Long,
    @ColumnInfo(name = "type")
    @TypeConverters(IntervalColorConverter::class)
    val color: IntervalColor
)
