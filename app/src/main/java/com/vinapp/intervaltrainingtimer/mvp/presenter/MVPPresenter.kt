package com.vinapp.intervaltrainingtimer.mvp.presenter

import com.vinapp.intervaltrainingtimer.mvp.view.MVPView

abstract class MVPPresenter<V: MVPView> {

    var view: V? = null

    open fun attachView(view: V) {
        this.view = view
    }

    open fun detachView() {
        this.view = null
    }

    abstract fun destroy()
}