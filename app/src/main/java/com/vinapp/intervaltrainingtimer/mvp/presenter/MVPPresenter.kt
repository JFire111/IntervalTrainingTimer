package com.vinapp.intervaltrainingtimer.mvp.presenter

import com.vinapp.intervaltrainingtimer.mvp.view.MVPView

interface MVPPresenter<V: MVPView> {

    fun attachView(view: V)

    fun detachView()

    fun destroy()
}