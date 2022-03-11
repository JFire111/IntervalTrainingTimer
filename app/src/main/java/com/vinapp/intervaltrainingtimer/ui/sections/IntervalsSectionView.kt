package com.vinapp.intervaltrainingtimer.ui.sections

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinapp.intervaltrainingtimer.databinding.FragmentIntervalListBinding
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.logic.timerediting.TimerEditingOutput
import com.vinapp.intervaltrainingtimer.mvp.IntervalSectionContract
import com.vinapp.intervaltrainingtimer.ui.SideButtonsClickListener
import kotlinx.android.synthetic.main.fragment_interval_list.view.*

class IntervalsSectionView(intervalsSectionEventListener: IntervalsSectionEventListener): Fragment(), IntervalSectionContract.View, IntervalsSectionAdapter.OnIntervalClickListener {

    override val title: String
        get() = "TimerSettingsSection"
    override val sectionFragment: Fragment
        get() = this
    override val sideButtonsClickListener: SideButtonsClickListener
        get() = presenter

    private var _binding: FragmentIntervalListBinding? = null
    private val binding
        get() = _binding!!
    private var presenter = IntervalsSectionPresenter(intervalsSectionEventListener)
    private lateinit var timerNameTextView: TextView
    private lateinit var numberOfRoundsTextView: TextView
    private lateinit var addRoundButton: FloatingActionButton
    private lateinit var removeRoundButton: FloatingActionButton
    private lateinit var intervalsRecyclerView: RecyclerView
    val timerEditingOutput: TimerEditingOutput
        get() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIntervalListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        initTimerNameTextView()
        numberOfRoundsTextView = view.numberOfRoundsTextView
        initAddRoundButton()
        initRemoveRoundButton()
        initIntervalsRecyclerView()
        return view
    }

    override fun showTimerName(name: String?) {
        if (name != null) {
            timerNameTextView.text = name
        } else {
            timerNameTextView.text = "Timer"
            presenter.onNameChanged(timerNameTextView.text.toString())
        }
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

    override fun onDeleteIntervalClick(position: Int) {
        presenter.onDeleteIntervalClick(position)
    }

    private fun initTimerNameTextView() {
        timerNameTextView = binding.root.timerNameEditTextText
        binding.root.setOnClickListener {
            timerNameTextView.clearFocus()
        }
        timerNameTextView.setOnFocusChangeListener { view, hasFocus ->
            (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                hideSoftInputFromWindow(view?.windowToken, 0)
                presenter.onNameChanged(timerNameTextView.text.toString())
            }
        }
    }

    private fun initAddRoundButton() {
        addRoundButton = binding.root.addRoundButton
        addRoundButton.setOnClickListener {
            presenter.addRound()
            timerNameTextView.clearFocus()
        }
    }

    private fun initRemoveRoundButton() {
        removeRoundButton = binding.root.removeRoundButton
        removeRoundButton.setOnClickListener {
            presenter.removeRound()
            timerNameTextView.clearFocus()
        }
    }

    private fun initIntervalsRecyclerView() {
        intervalsRecyclerView = binding.root.intervalsRecyclerView
        intervalsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
    }
}