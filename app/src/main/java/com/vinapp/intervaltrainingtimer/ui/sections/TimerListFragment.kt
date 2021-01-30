package com.vinapp.intervaltrainingtimer.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimersListBinding
import kotlinx.android.synthetic.main.fragment_timers_list.view.*

class TimerListFragment: Fragment() {

    private var _binding: FragmentTimersListBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var timersRecyclerView: RecyclerView
    private lateinit var timerList: List<Any>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimersListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        timersRecyclerView = view.timersRecyclerView
        timersRecyclerView.layoutManager = LinearLayoutManager(view.context)
        timersRecyclerView.adapter = TimerListAdapter(timerList)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}