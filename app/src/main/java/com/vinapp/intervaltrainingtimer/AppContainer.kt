package com.vinapp.intervaltrainingtimer

import android.content.Context
import androidx.room.Room
import com.vinapp.intervaltrainingtimer.data.database.Database
import com.vinapp.intervaltrainingtimer.data.source.interval.IntervalRepositoryImpl
import com.vinapp.intervaltrainingtimer.data.source.timer.TimerRepositoryImpl
import com.vinapp.intervaltrainingtimer.utils.SoundPlayerImpl

class AppContainer(context: Context) {

    val database = Room
        .databaseBuilder(context, Database::class.java, "database")
        .fallbackToDestructiveMigration()
        .build()
    val timerRepository = TimerRepositoryImpl(
        timerDao = database.timerDao(),
        intervalDao = database.intervalDao(),
    )
    val intervalRepository = IntervalRepositoryImpl(
        intervalDao = database.intervalDao()
    )
    val soundPlayer = SoundPlayerImpl(
        context = context
    )
}