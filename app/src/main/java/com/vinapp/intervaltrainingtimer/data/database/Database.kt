package com.vinapp.intervaltrainingtimer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinapp.intervaltrainingtimer.data.database.converters.IntervalTypeConverter
import com.vinapp.intervaltrainingtimer.entities.Timer

@Database(entities = arrayOf(Timer::class), version = 1)
@TypeConverters(IntervalTypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun timerDao(): TimerDao
}