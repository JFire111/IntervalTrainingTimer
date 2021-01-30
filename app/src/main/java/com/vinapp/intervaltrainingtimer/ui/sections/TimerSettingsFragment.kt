package com.vinapp.intervaltrainingtimer.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimerSettingsBinding

class TimerSettingsFragment: Fragment() {

    private var _binding: FragmentTimerSettingsBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var intervalsRecyclerView: RecyclerView
    private lateinit var intervalList: List<Any>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimerSettingsBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        intervalsRecyclerView = binding.intervalsRecyclerView
        intervalsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        intervalsRecyclerView.adapter = TimerSettingsAdapter(intervalList)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}