package com.vinapp.intervaltrainingtimer.ui.sections

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.FragmentIntervalListBinding
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract

class IntervalsSectionView(private val intervalSectionPresenter: IntervalSectionContract.Presenter): Fragment(), IntervalSectionContract.View, IntervalsSectionAdapter.OnIntervalClickListener {

    override val title: String
        get() = "TimerSettingsSection"
    override val sectionFragment: Fragment
        get() = this

    private var _binding: FragmentIntervalListBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var intervalsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIntervalListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        intervalsRecyclerView = binding.intervalsRecyclerView
        intervalsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun showIntervalList(intervalList: ArrayList<Interval>) {
        if (intervalsRecyclerView.adapter == null) {
            intervalsRecyclerView.adapter = IntervalsSectionAdapter(intervalList, this)
        } else {
            intervalsRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()
        intervalSectionPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        intervalSectionPresenter.detachView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        intervalSectionPresenter.destroy()
    }

    override fun onIntervalClick(position: Int) {
        intervalSectionPresenter.onIntervalClick()
    }

    override fun onAddIntervalClick() {
        intervalSectionPresenter.onAddIntervalClick()
    }
}