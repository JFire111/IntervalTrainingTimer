package com.vinapp.intervaltrainingtimer

import android.app.Application
import com.vinapp.intervaltrainingtimer.data.repositories.IntervalRepository
import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.mvp.model.IntervalMVPModel
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class App: Application() {

    lateinit var intervalRepository: IntervalMVPModel
    lateinit var timerRepository: TimerMVPModel

    override fun onCreate() {
        super.onCreate()
        intervalRepository = IntervalRepository()
        timerRepository = TimerRepository()
    }
}