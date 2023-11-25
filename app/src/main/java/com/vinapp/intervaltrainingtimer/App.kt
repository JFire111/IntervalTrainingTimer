package com.vinapp.intervaltrainingtimer

import android.app.Application
import com.vinapp.intervaltrainingtimer.data.database.Database
import com.vinapp.intervaltrainingtimer.logic.timer_list.TimerListInteractor
import com.vinapp.intervaltrainingtimer.logic.timer_editing.TimerEditingInteractor
import com.vinapp.intervaltrainingtimer.services.TimerServiceController

class App: Application() {

    var timerEditingInteractor: TimerEditingInteractor? = null
    var timerListInteractor: TimerListInteractor? = null
    var serviceController: TimerServiceController? = null

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
//        database = Room.databaseBuilder(this, Database::class.java, "database").allowMainThreadQueries().build()
//        timerRepository = TimerRepository(database!!.timerDao())
//        timerEditingInteractor = TimerEditingInteractor(timerRepository!!, null)
//        timerListInteractor = TimerListInteractor(timerRepository!!, null)
//        serviceController = TimerServiceController(this)
    }
}