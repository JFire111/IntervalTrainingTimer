package com.vinapp.intervaltrainingtimer.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimersListBinding
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.TimerListContract
import kotlinx.android.synthetic.main.fragment_timers_list.view.*

class TimerListSection(private val timerListPresenter: TimerListContract.Presenter): Fragment(), TimerListContract.View {

    override val title: String
        get() = "TimerListSection"
    override val sectionFragment: Fragment
        get() = this

    private var _binding: FragmentTimersListBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var timersRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimersListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        timersRecyclerView = view.timersRecyclerView
        timersRecyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun showTimerList(timerList: List<Timer>) {
        timersRecyclerView.adapter = TimerListAdapter(timerList)
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
}