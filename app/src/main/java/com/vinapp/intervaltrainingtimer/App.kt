package com.vinapp.intervaltrainingtimer

import android.app.Application

class App: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}