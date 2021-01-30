package com.vinapp.intervaltrainingtimer.mvp.view.sections

import androidx.fragment.app.Fragment
import com.vinapp.intervaltrainingtimer.mvp.view.MVPView

interface SectionView: MVPView {

    val title: String
    val sectionFragment: Fragment
}