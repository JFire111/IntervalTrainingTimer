package com.vinapp.intervaltrainingtimer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vinapp.intervaltrainingtimer.databinding.ActivityMainBinding
import com.vinapp.intervaltrainingtimer.mvp.view.main.MainView
import com.vinapp.intervaltrainingtimer.ui.sections.TimerListSection
import com.vinapp.intervaltrainingtimer.ui.sections.TimerSettingsSection

class MainActivity: AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainToolbar: Toolbar
    private lateinit var sectionsTabLayout: TabLayout
    private lateinit var sectionsViewPager: ViewPager2

    private val sections = listOf(
            TimerSettingsSection(),
            TimerListSection()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainToolbar = binding.mainToolbar
        sectionsTabLayout = binding.sectionsTabLayout
        sectionsViewPager = binding.sectionsViewPager

        val sectionsAdapter = SectionsAdapter(sections, this)
        sectionsViewPager.adapter = sectionsAdapter
        TabLayoutMediator(sectionsTabLayout, sectionsViewPager) {
            tab, position -> tab.text = sections[position].title
        }.attach()
    }

    override fun showStartButton() {
        TODO("Not yet implemented")
    }

    override fun showSaveButton() {
        TODO("Not yet implemented")
    }

    override fun showDeleteButton() {
        TODO("Not yet implemented")
    }

}