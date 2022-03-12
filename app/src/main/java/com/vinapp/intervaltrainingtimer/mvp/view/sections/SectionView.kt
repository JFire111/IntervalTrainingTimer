package com.vinapp.intervaltrainingtimer.mvp.view.sections

import androidx.fragment.app.Fragment
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView
import com.vinapp.intervaltrainingtimer.ui.OnActionButtonsClickListener

interface SectionView: MVPView {

    val title: String
    val sectionFragment: Fragment
    val onActionButtonsClickListener: OnActionButtonsClickListener
}