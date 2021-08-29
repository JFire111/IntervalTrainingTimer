package com.vinapp.intervaltrainingtimer

import android.app.Application
import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListInteractor
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingInteractor
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class App: Application() {

    val timerRepository: TimerMVPModel = TimerRepository()
    val timerEditingInteractor = TimerEditingInteractor(timerRepository, null)
    val getTimersInteractor = TimerListInteractor(timerRepository, null)
}