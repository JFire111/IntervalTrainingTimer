package com.vinapp.intervaltrainingtimer.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.App
import com.vinapp.intervaltrainingtimer.databinding.FragmentIntervalListBinding
import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener

class IntervalsSectionView(sectionsEventHandler: SectionsEventHandler): Fragment(), IntervalSectionContract.View, IntervalsSectionAdapter.OnIntervalClickListener {

    override val title: String
        get() = "TimerSettingsSection"
    override val sectionFragment: Fragment
        get() = this
    override val sideButtonsClickListener: SideButtonsClickListener
        get() = presenter

    private var _binding: FragmentIntervalListBinding? = null
    private val binding
        get() = _binding!!
    private var presenter = IntervalsSectionPresenter(sectionsEventHandler)
    private lateinit var intervalsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIntervalListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        val app = activity?.applicationContext as App
        presenter.intervalRepository = app.intervalRepository
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
        presenter.attachView(this)
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

    override fun onIntervalClick(position: Int) {
        presenter.onIntervalClick(position)
    }

    override fun onAddIntervalClick() {
        presenter.onAddIntervalClick()
    }
}