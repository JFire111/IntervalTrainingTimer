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
import com.vinapp.intervaltrainingtimer.mvp.TimerSectionContract
import kotlinx.android.synthetic.main.fragment_timer_list.view.*

class TimersSectionView(private val timerListPresenter: TimerSectionContract.Presenter): Fragment(), TimerSectionContract.View, TimersSectionAdapter.OnTimerClickListener {

    override val title: String
        get() = "TimerListSection"
    override val sectionFragment: Fragment
        get() = this

    private var _binding: FragmentTimerListBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var timersRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimerListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        timersRecyclerView = view.timersRecyclerView
        timersRecyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun showTimerList(timerList: ArrayList<Timer>) {
        if (timersRecyclerView.adapter == null) {
            timersRecyclerView.adapter = TimersSectionAdapter(timerList, this)
        } else {
            timersRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()
        timerListPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        timerListPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        timerListPresenter.destroy()
    }

    override fun onTimerClick(position: Int) {
        timerListPresenter.onTimerItemClick(position)
    }

    override fun onAddTimerClick() {
        timerListPresenter.onAddTimerClick()
    }
}