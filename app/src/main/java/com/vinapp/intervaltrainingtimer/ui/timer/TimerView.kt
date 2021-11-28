package com.vinapp.intervaltrainingtimer.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimerBinding
import com.vinapp.intervaltrainingtimer.logic.timer.TimerInput
import com.vinapp.intervaltrainingtimer.mvp.TimerContract
import kotlinx.android.synthetic.main.fragment_timer.view.*

class TimerView(val timerInput: TimerInput): Fragment(), TimerContract.View {

    private var presenter: TimerPresenter? = null
    private var _binding: FragmentTimerBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var infoTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var timerActionButton: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = TimerPresenter(timerInput)
        _binding = FragmentTimerBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        infoTextView = view.infoTextView
        timeTextView = view.timeTextView
        timerActionButton = view.timerActionButton

        timerActionButton.setOnClickListener { presenter!!.onTimerActionButtonClick() }
        return view
    }

    override fun onStart() {
        super.onStart()
        presenter!!.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter!!.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter!!.destroy()
    }

    override fun showMessage(message: String) {
    }

    override fun showTime(time: String) {
        timeTextView.text = time
    }

    override fun setColorByIntervalType(type: IntervalType) {
        context?.let {
            when (type) {
                IntervalType.WORK -> view!!.setBackgroundColor(ContextCompat.getColor(it, R.color.colorOfAction))
                IntervalType.REST -> view!!.setBackgroundColor(ContextCompat.getColor(it, R.color.colorOfRestGreen))
            }
        }
    }

    override fun setDefaultColor() {
        context?.let {
            view!!.setBackgroundColor(ContextCompat.getColor(it, R.color.colorOfMenu))
        }
    }
}