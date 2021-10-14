package com.vinapp.intervaltrainingtimer.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimerListBinding
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.logic.gettimers.TimerListOutput
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener
import kotlinx.android.synthetic.main.fragment_timer_list.view.*

class TimersSectionView(sectionsEventHandler: SectionsEventHandler): Fragment(), TimerSectionContract.View, TimersSectionAdapter.OnTimerClickListener {

    override val title: String
        get() = "TimerListSection"
    override val sectionFragment: Fragment
        get() = this
    override val sideButtonsClickListener: SideButtonsClickListener
        get() = presenter

    private var _binding: FragmentTimerListBinding? = null
    private val binding
        get() = _binding!!
    private var presenter = TimersSectionPresenter(sectionsEventHandler)
    private lateinit var timersRecyclerView: RecyclerView
    val timerListOutput: TimerListOutput
        get() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimerListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        timersRecyclerView = view.timersRecyclerView
        timersRecyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun showTimerList(timerList: List<Timer>) {
        if (timersRecyclerView.adapter == null) {
            timersRecyclerView.adapter = TimersSectionAdapter(timerList, this)
        } else {
            timersRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.destroy()
    }

    override fun onTimerClick(position: Int) {
        presenter.onTimerItemClick(position)
    }

    override fun onAddTimerClick() {
        presenter.onAddTimerClick()
    }

    override fun onDeleteClickListener(position: Int) {
        presenter.onDeleteTimerClick(position)
    }
}