package com.vinapp.intervaltrainingtimer.mvp.view.sections

import androidx.fragment.app.Fragment
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

interface SectionView: MVPView {

    val title: String
    val sectionFragment: Fragment
    val sideButtonsClickListener: SideButtonsClickListener
}