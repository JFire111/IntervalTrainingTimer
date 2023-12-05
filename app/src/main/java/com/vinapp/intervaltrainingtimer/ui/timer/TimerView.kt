package com.vinapp.intervaltrainingtimer.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimerBinding
import com.vinapp.intervaltrainingtimer.domain.Timer
import com.vinapp.intervaltrainingtimer.entities.TimerEntity
import com.vinapp.intervaltrainingtimer.mvp.TimerContract
import com.vinapp.intervaltrainingtimer.services.TimerServiceController

class TimerView(private val timer: Timer, private val serviceController: TimerServiceController): Fragment(), TimerContract.View {

    private var presenter: TimerPresenter? = null
    private var _binding: FragmentTimerBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var infoTextView: TextView
    private lateinit var delaySeekBar: SeekBar
    private lateinit var timeTextView: TextView
    private lateinit var timerActionButton: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = TimerPresenter(timer, serviceController)
        _binding = FragmentTimerBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        infoTextView = binding.infoTextView
        timeTextView = binding.timeTextView
        initTimerActionButton()
        initDelaySeekBar()
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

    override fun showDelay(delay: Int) {
        infoTextView.text = "${getString(R.string.delayBeforeStart)}: $delay"
        delaySeekBar.progress = delay
    }

    override fun hideMessage() {
        infoTextView.visibility = View.GONE
    }

    override fun showDelaySeekBar() {
        delaySeekBar.visibility = View.VISIBLE
    }

    override fun hideDelaySeekBar() {
        delaySeekBar.visibility = View.GONE
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

    override fun setColorByIntervalType(type: IntervalColor) {
        context?.let {
            when (type) {
                IntervalColor.RED -> requireView().setBackgroundColor(ContextCompat.getColor(it, R.color.red))
                IntervalColor.GREEN -> requireView().setBackgroundColor(ContextCompat.getColor(it, R.color.green))
                else -> {}
            }
        }
    }

    override fun setDefaultColor() {
        context?.let {
            requireView().setBackgroundColor(ContextCompat.getColor(it, R.color.white))
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

    private fun initTimerActionButton() {
        timerActionButton = binding.timerActionButton
        timerActionButton.setOnClickListener {
            presenter!!.onTimerActionButtonClick()
        }
    }

    private fun initDelaySeekBar() {
        delaySeekBar = binding.delaySeekBar
        delaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                presenter!!.changeDelay(seekBar!!.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}