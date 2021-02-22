package com.vinapp.intervaltrainingtimer.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimerSettingsBinding
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract

class IntervalSectionSection(private val intervalListPresenter: IntervalSectionContract.Presenter): Fragment(), IntervalSectionContract.View, IntervalSectionAdapter.OnIntervalClickListener {

    override val title: String
        get() = "TimerSettingsSection"
    override val sectionFragment: Fragment
        get() = this

    private var _binding: FragmentTimerSettingsBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var intervalsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimerSettingsBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        intervalsRecyclerView = binding.intervalsRecyclerView
        intervalsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        var intervalList: ArrayList<Interval> = ArrayList()
        intervalsRecyclerView.adapter = IntervalSectionAdapter(intervalList, this)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showIntervalList(intervalList: ArrayList<Interval>) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        intervalListPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        intervalListPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        intervalListPresenter.destroy()
    }

    override fun onIntervalClick(position: Int) {
        intervalListPresenter.onIntervalClick()
    }

    override fun onAddIntervalClick() {
        intervalListPresenter.onAddIntervalClick()
    }
}