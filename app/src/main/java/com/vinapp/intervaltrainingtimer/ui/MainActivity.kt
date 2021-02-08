package com.vinapp.intervaltrainingtimer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vinapp.intervaltrainingtimer.databinding.ActivityMainBinding
import com.vinapp.intervaltrainingtimer.mvp.MainContract
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
import com.vinapp.intervaltrainingtimer.ui.sections.TimerListPresenter
import com.vinapp.intervaltrainingtimer.ui.sections.TimerListSection
import com.vinapp.intervaltrainingtimer.ui.sections.TimerSettingsPresenter
import com.vinapp.intervaltrainingtimer.ui.sections.TimerSettingsSection

class MainActivity: AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainToolbar: Toolbar
    private lateinit var sectionsTabLayout: TabLayout
    private lateinit var sectionsViewPager: ViewPager2

    private val mainPresenter = MainPresenter()
    private val sections = listOf<SectionView>(
            TimerSettingsSection(TimerSettingsPresenter()),
            TimerListSection(TimerListPresenter(mainPresenter))
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

    override fun showSection(position: Int) {
        sectionsViewPager.currentItem = position
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

    override fun onStart() {
        super.onStart()
        mainPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.destroy()
    }
}