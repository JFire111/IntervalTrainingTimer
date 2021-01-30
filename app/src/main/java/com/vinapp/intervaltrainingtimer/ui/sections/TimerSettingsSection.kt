package com.vinapp.intervaltrainingtimer.ui.sections

import androidx.fragment.app.Fragment
import com.vinapp.intervaltrainingtimer.mvp.view.sections.TimerSettingsSectionView

class TimerSettingsSection: TimerSettingsSectionView {

    override val title: String
        get() = "TimerSettingsSection"
    override val sectionFragment: Fragment
        get() = TimerSettingsFragment()

    override fun showHintItem() {
        TODO("Not yet implemented")
    }

    override fun showAddIntervalItem() {
        TODO("Not yet implemented")
    }

    override fun showIntervalList() {
        TODO("Not yet implemented")
    }
}