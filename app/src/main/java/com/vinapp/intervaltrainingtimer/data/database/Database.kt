package com.vinapp.intervaltrainingtimer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinapp.intervaltrainingtimer.data.database.converters.IntervalColorConverter
import com.vinapp.intervaltrainingtimer.data.entities.IntervalEntity
import com.vinapp.intervaltrainingtimer.data.entities.TimerEntity

@Database(
    entities = [
        TimerEntity::class,
        IntervalEntity::class
    ],
    version = 2
)
@TypeConverters(IntervalColorConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun timerDao(): TimerDao
    abstract fun intervalDao(): IntervalDao
}