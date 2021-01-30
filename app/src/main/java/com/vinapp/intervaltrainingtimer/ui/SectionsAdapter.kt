package com.vinapp.intervaltrainingtimer.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView

class SectionsAdapter(
        private val sections: List<SectionView>,
        fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return sections.size
    }

    override fun createFragment(position: Int): Fragment {
        return sections[position].sectionFragment
    }
}