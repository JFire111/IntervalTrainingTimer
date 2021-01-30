package com.vinapp.intervaltrainingtimer.ui.sections

import androidx.fragment.app.Fragment
import com.vinapp.intervaltrainingtimer.mvp.view.sections.TimerListSectionView

class TimerListSection: TimerListSectionView {

    override val title: String
        get() = "TimerListSection"
    override val sectionFragment: Fragment
        get() = TimerListFragment()

    override fun showTimerList() {
        TODO("Not yet implemented")
    }
}