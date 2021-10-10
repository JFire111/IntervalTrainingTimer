package com.vinapp.intervaltrainingtimer

import android.app.Application
import androidx.room.Room
import com.vinapp.intervaltrainingtimer.data.database.Database
import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListInteractor
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingInteractor
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class App: Application() {

    var database: Database? = null
    var timerRepository: TimerMVPModel? = null
    var timerEditingInteractor: TimerEditingInteractor? = null
    var getTimersInteractor: TimerListInteractor? = null

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, Database::class.java, "database").allowMainThreadQueries().build()
        timerRepository = TimerRepository(database!!.timerDao())
        timerEditingInteractor = TimerEditingInteractor(timerRepository!!, null)
        getTimersInteractor = TimerListInteractor(timerRepository!!, null)
    }
}