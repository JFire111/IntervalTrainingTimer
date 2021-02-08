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
import com.vinapp.intervaltrainingtimer.mvp.TimerSettingsContract

class TimerSettingsSection(private val timerSettingsPresenter: TimerSettingsContract.Presenter): Fragment(), TimerSettingsContract.View {

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
        var intervalList: List<Interval> = emptyList()
        intervalsRecyclerView.adapter = TimerSettingsAdapter(intervalList)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showHintItem() {
        TODO("Not yet implemented")
    }

    override fun showAddIntervalItem() {
        TODO("Not yet implemented")
    }

    override fun showIntervalList() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        timerSettingsPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        timerSettingsPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerSettingsPresenter.destroy()
    }
}