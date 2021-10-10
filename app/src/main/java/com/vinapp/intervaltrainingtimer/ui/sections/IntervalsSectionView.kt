package com.vinapp.intervaltrainingtimer.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinapp.intervaltrainingtimer.databinding.FragmentIntervalListBinding
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingOutput
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.SectionsEventHandler
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener
import kotlinx.android.synthetic.main.fragment_interval_list.view.*

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
    private lateinit var numberOfRoundsTextView: TextView
    private lateinit var addRoundButton: FloatingActionButton
    private lateinit var removeRoundButton: FloatingActionButton
    private lateinit var intervalsRecyclerView: RecyclerView
    val timerEditingOutput: TimerEditingOutput
        get() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIntervalListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        numberOfRoundsTextView = view.numberOfRoundsTextView
        addRoundButton = view.addRoundButton
        removeRoundButton = view.removeRoundButton
        intervalsRecyclerView = view.intervalsRecyclerView
        intervalsRecyclerView.layoutManager = LinearLayoutManager(view.context)

        addRoundButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                presenter.addRound()
            }
        })

        removeRoundButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                presenter.removeRound()
            }
        })

        return view
    }

    override fun showNumberOfRounds(numberOfRounds: Int) {
        numberOfRoundsTextView.text = numberOfRounds.toString()
    }

    override fun showIntervalList(intervalList: List<Interval>) {
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