package com.vinapp.intervaltrainingtimer.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView

class SectionsAdapter(private val sections: List<SectionView>, fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun createFragment(position: Int): Fragment {
        return sections[position].sectionFragment
    }
}