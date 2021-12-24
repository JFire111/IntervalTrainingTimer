package com.vinapp.intervaltrainingtimer.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimerBinding
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.TimerContract
import com.vinapp.intervaltrainingtimer.services.TimerServiceController
import kotlinx.android.synthetic.main.fragment_timer.view.*

class TimerView(private val timer: Timer, private val serviceController: TimerServiceController): Fragment(), TimerContract.View {

    private var presenter: TimerPresenter? = null
    private var _binding: FragmentTimerBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var infoTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var timerActionButton: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = TimerPresenter(timer, serviceController)
        _binding = FragmentTimerBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        infoTextView = view.infoTextView
        timeTextView = view.timeTextView
        timerActionButton = view.timerActionButton

        timerActionButton.setOnClickListener {
            presenter!!.onTimerActionButtonClick()
        }
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
        infoTextView.text = message
    }

    override fun showTime(time: String) {
        if (timeTextView.isInvisible) {
            timeTextView.visibility = View.VISIBLE
        }
        timeTextView.text = time
    }

    override fun hideTime() {
        timeTextView.visibility = View.INVISIBLE
    }

    override fun setColorByIntervalType(type: IntervalType) {
        context?.let {
            when (type) {
                IntervalType.WORK -> view!!.setBackgroundColor(ContextCompat.getColor(it, R.color.orange))
                IntervalType.REST -> view!!.setBackgroundColor(ContextCompat.getColor(it, R.color.green))
            }
        }
    }

    override fun setDefaultColor() {
        context?.let {
            view!!.setBackgroundColor(ContextCompat.getColor(it, R.color.white))
        }
    }

    override fun setActionButtonIconByState(state: TimerActionButtonState) {
        when (state) {
            TimerActionButtonState.PAUSE_WHITE -> timerActionButton.setImageResource(R.drawable.pause_white)
            TimerActionButtonState.PAUSE_RED -> timerActionButton.setImageResource(R.drawable.pause_red)
            TimerActionButtonState.PAUSE_GREEN -> timerActionButton.setImageResource(R.drawable.pause_green)
            TimerActionButtonState.PLAY_WHITE -> timerActionButton.setImageResource(R.drawable.play_white)
            TimerActionButtonState.PLAY_RED -> timerActionButton.setImageResource(R.drawable.play_red)
            TimerActionButtonState.PLAY_GREEN -> timerActionButton.setImageResource(R.drawable.play_green)
        }
    }
}