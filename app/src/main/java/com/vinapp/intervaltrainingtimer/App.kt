package com.vinapp.intervaltrainingtimer

import android.app.Application
import com.vinapp.intervaltrainingtimer.data.repositories.IntervalRepository

class App: Application() {

    lateinit var intervalRepository: IntervalRepository

    override fun onCreate() {
        super.onCreate()
        intervalRepository = IntervalRepository()
    }
}