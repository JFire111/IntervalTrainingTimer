package com.vinapp.intervaltrainingtimer

import android.content.Context
import androidx.room.Room
import com.vinapp.intervaltrainingtimer.data.database.Database
import com.vinapp.intervaltrainingtimer.data.interval.IntervalRepositoryImpl
import com.vinapp.intervaltrainingtimer.data.timer.TimerRepositoryImpl

class AppContainer(context: Context) {

    val database = Room.databaseBuilder(context, Database::class.java, "database").build()
    val timerRepository = TimerRepositoryImpl(
        timerDao = database.timerDao(),
        intervalDao = database.intervalDao(),
    )
    val intervalRepository = IntervalRepositoryImpl(
        intervalDao = database.intervalDao()
    )
}