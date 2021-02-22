package com.vinapp.intervaltrainingtimer.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vinapp.intervaltrainingtimer.databinding.ActivityMainBinding
import com.vinapp.intervaltrainingtimer.mvp.MainContract
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
import com.vinapp.intervaltrainingtimer.ui.sections.TimerSectionPresenter
import com.vinapp.intervaltrainingtimer.ui.sections.TimerSectionSection
import com.vinapp.intervaltrainingtimer.ui.sections.IntervalSectionPresenter
import com.vinapp.intervaltrainingtimer.ui.sections.IntervalSectionSection

class MainActivity: AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainToolbar: Toolbar
    private lateinit var sectionsTabLayout: TabLayout
    private lateinit var sectionsViewPager: ViewPager2
    private lateinit var startButton: FloatingActionButton
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button

    private val mainPresenter = MainPresenter()
    private val sections = listOf<SectionView>(
            IntervalSectionSection(IntervalSectionPresenter(mainPresenter)),
            TimerSectionSection(TimerSectionPresenter(mainPresenter))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainToolbar = binding.mainToolbar
        sectionsTabLayout = binding.sectionsTabLayout
        sectionsViewPager = binding.sectionsViewPager
        startButton = binding.floatingActionButton
        leftButton = binding.leftButton
        rightButton = binding.rightButton

        val sectionsAdapter = SectionsAdapter(sections, this)
        sectionsViewPager.adapter = sectionsAdapter
        sectionsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    mainPresenter.sectionSelected(sectionsViewPager.currentItem)
                } else {
                    mainPresenter.sectionScrolled()
                }
            }
        })

        TabLayoutMediator(sectionsTabLayout, sectionsViewPager) {
            tab, position -> tab.text = sections[position].title
        }.attach()
    }

    override fun showSection(position: Int) {
        sectionsViewPager.currentItem = position
    }

    override fun showStartButton() {
        startButton.visibility = View.VISIBLE
    }

    override fun hideStartButton() {
        startButton.visibility = View.GONE
    }

    override fun showLeftButton(text: String) {
        leftButton.text = text
        leftButton.visibility = View.VISIBLE
    }

    override fun hideLeftButton() {
        leftButton.visibility = View.GONE
    }

    override fun showRightButton(text: String) {
        rightButton.text = text
        rightButton.visibility = View.VISIBLE
    }

    override fun hideRightButton() {
        rightButton.visibility = View.GONE
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