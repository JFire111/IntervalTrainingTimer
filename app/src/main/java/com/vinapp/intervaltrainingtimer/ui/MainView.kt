package com.vinapp.intervaltrainingtimer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.databinding.FragmentMainBinding
import com.vinapp.intervaltrainingtimer.mvp.MainContract
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
import com.vinapp.intervaltrainingtimer.ui.sections.IntervalsSectionPresenter
import com.vinapp.intervaltrainingtimer.ui.sections.IntervalsSectionView
import com.vinapp.intervaltrainingtimer.ui.sections.TimersSectionPresenter
import com.vinapp.intervaltrainingtimer.ui.sections.TimersSectionView
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainView(private val mainPresenter: MainPresenter) : Fragment(), MainContract.View {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainToolbar: Toolbar
    private lateinit var sectionsTabLayout: TabLayout
    private lateinit var sectionsViewPager: ViewPager2
    private lateinit var startButton: FloatingActionButton
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button

    private val intervalSectionPresenter = IntervalsSectionPresenter(mainPresenter)
    private val timersSectionPresenter = TimersSectionPresenter(mainPresenter)

    private val sections = listOf<SectionView>(
            IntervalsSectionView(intervalSectionPresenter),
            TimersSectionView(timersSectionPresenter)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        mainToolbar = view.mainToolbar
        sectionsTabLayout = view.sectionsTabLayout
        sectionsViewPager = view.sectionsViewPager
        startButton = view.floatingActionButton
        leftButton = view.leftButton
        rightButton = view.rightButton

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

        return view
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.detachView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.destroy()
    }

    override fun showSection(position: Int) {
        sectionsViewPager.currentItem = position
    }

    override fun showIntervalKeyboard(onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener) {
        val intervalKeyboardView = IntervalKeyboardView(IntervalKeyboardPresenter(mainPresenter, onIntervalKeyboardListener))
        fragmentManager!!.beginTransaction().replace(R.id.mainLayout, intervalKeyboardView).addToBackStack("").attach(intervalKeyboardView).commit()
    }

    override fun hideIntervalKeyboard() {
        fragmentManager!!.popBackStack()
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
}