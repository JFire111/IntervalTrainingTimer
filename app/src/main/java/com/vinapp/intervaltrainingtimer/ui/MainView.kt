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
import com.vinapp.intervaltrainingtimer.App
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.databinding.FragmentMainBinding
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.MainContract
import com.vinapp.intervaltrainingtimer.mvp.view.sections.SectionView
import com.vinapp.intervaltrainingtimer.services.TimerServiceController
import com.vinapp.intervaltrainingtimer.ui.sections.IntervalsSectionView
import com.vinapp.intervaltrainingtimer.ui.sections.TimersSectionView
import com.vinapp.intervaltrainingtimer.ui.timer.TimerView
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainView : Fragment(), MainContract.View {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var presenter: MainPresenter
    private lateinit var mainToolbar: Toolbar
    private lateinit var sectionsTabLayout: TabLayout
    private lateinit var sectionsViewPager: ViewPager2
    private lateinit var startButton: FloatingActionButton
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var sections: List<SectionView>
    private lateinit var intervalsSectionView: IntervalsSectionView
    private lateinit var timersSectionView: TimersSectionView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = activity?.applicationContext as App
        val timerEditingInteractor = app.timerEditingInteractor
        val timerListInteractor = app.timerListInteractor
        presenter = MainPresenter(0, timerEditingInteractor!!, timerListInteractor!!, app.serviceController!!)
        intervalsSectionView = IntervalsSectionView(presenter)
        timersSectionView = TimersSectionView(presenter)
        timerEditingInteractor.registerOutput(intervalsSectionView.timerEditingOutput)
        timerListInteractor.registerOutput(timersSectionView.timerListOutput)
        sections = listOf<SectionView>(
                intervalsSectionView,
                timersSectionView
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        mainToolbar = view.mainToolbar
        sectionsTabLayout = view.sectionsTabLayout
        sectionsViewPager = view.sectionsViewPager
        startButton = view.floatingActionButton
        leftButton = view.leftButton
        rightButton = view.rightButton

        configureViewPager()

        TabLayoutMediator(sectionsTabLayout, sectionsViewPager) {
            tab, position -> tab.text = sections[position].title
        }.attach()

        setClickListeners()
        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
        presenter.sectionSelected(sectionsViewPager.currentItem, sections[sectionsViewPager.currentItem].sideButtonsClickListener)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun showSection(position: Int) {
        sectionsViewPager.currentItem = position
    }

    override fun showTimerScreen(timer: Timer, serviceController: TimerServiceController) {
        val transition = fragmentManager!!.beginTransaction()
        transition.replace(R.id.mainFragmentContainer, TimerView(timer, serviceController)).addToBackStack("TIMER_SCREEN").commit()
    }

    override fun showIntervalKeyboard(interval: Interval?, onIntervalKeyboardListener: SectionsEventHandler.OnIntervalKeyboardListener) {
        val intervalKeyboardView = IntervalKeyboardView(IntervalKeyboardPresenter(presenter, onIntervalKeyboardListener, interval))
        fragmentManager!!.beginTransaction().replace(R.id.mainFragmentContainer, intervalKeyboardView).addToBackStack("INTERVAL_KEYBOARD_SCREEN").attach(intervalKeyboardView).commit()
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

    override fun showClearButton() {
        leftButton.text = "Clear"
        leftButton.visibility = View.VISIBLE
    }

    override fun hideClearButton() {
        if (leftButton.visibility == View.VISIBLE) {
            leftButton.visibility = View.GONE
        }
    }

    override fun showSaveButton() {
        rightButton.text = "Save"
        rightButton.visibility = View.VISIBLE
    }

    override fun hideSaveButton() {
        if (rightButton.visibility == View.VISIBLE) {
            rightButton.visibility = View.GONE
        }
    }

    override fun showEditButton() {
        leftButton.text = "Edit"
        leftButton.visibility = View.VISIBLE
    }

    override fun hideEditButton() {
        if (leftButton.visibility == View.VISIBLE) {
            leftButton.visibility = View.GONE
        }
    }

    private fun setClickListeners() {
        startButton.setOnClickListener {
            presenter.onStartButtonClick()
        }
        rightButton.setOnClickListener {
            when (sectionsViewPager.currentItem) {
                0 -> presenter.onSaveButtonClick()
            }
        }
        leftButton.setOnClickListener {
            when (sectionsViewPager.currentItem) {
                0 -> presenter.onClearButtonClick()
                1 -> presenter.onEditButtonClick()
            }
        }
    }

    private fun configureViewPager() {
        val sectionsAdapter = SectionsAdapter(sections, this)
        sectionsViewPager.adapter = sectionsAdapter
        sectionsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    presenter.sectionSelected(sectionsViewPager.currentItem, sections[sectionsViewPager.currentItem].sideButtonsClickListener)
                } else {
                    presenter.sectionScrolled()
                }
            }
        })
    }
}