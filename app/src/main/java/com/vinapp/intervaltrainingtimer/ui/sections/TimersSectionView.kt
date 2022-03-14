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
import com.vinapp.intervaltrainingtimer.logic.timer_list.TimerListOutput
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import com.vinapp.intervaltrainingtimer.ui.OnActionButtonsClickListener

class TimersSectionView(timersSectionEventListener: TimersSectionEventListener): Fragment(), TimerSectionContract.View, TimersSectionAdapter.OnTimerClickListener {

    override val title: String
        get() = "TimerListSection"
    override val sectionFragment: Fragment
        get() = this
    override val onActionButtonsClickListener: OnActionButtonsClickListener
        get() = presenter

    private var _binding: FragmentTimerListBinding? = null
    private val binding
        get() = _binding!!
    private var presenter = TimersSectionPresenter(timersSectionEventListener)
    private lateinit var timersRecyclerView: RecyclerView
    val timerListOutput: TimerListOutput
        get() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTimerListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        initTimerRecyclerView()
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

    private fun initTimerRecyclerView() {
        timersRecyclerView = binding.timersRecyclerView
        timersRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
    }
}