package com.vinapp.intervaltrainingtimer.mvp.presenter.sections

import com.vinapp.intervaltrainingtimer.mvp.presenter.MVPPresenter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
import com.vinapp.intervaltrainingtimer.ui.Navigator

abstract class SectionPresenter<V: SectionView>(val navigator: Navigator): MVPPresenter<V>()