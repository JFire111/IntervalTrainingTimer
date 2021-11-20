package com.vinapp.intervaltrainingtimer.ui.timer

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinapp.intervaltrainingtimer.databinding.FragmentTimerBinding
import com.vinapp.intervaltrainingtimer.mvp.TimerContract
import kotlinx.android.synthetic.main.fragment_timer.view.*

class TimerView: Fragment(), TimerContract.View {

    private var _binding: FragmentTimerBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var infoTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var timerActionButton: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTimerBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        infoTextView = view.infoTextView
        timeTextView = view.timeTextView
        timerActionButton = view.timerActionButton
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showMessage(message: String) {
    }

    override fun showTime(long: Long) {
    }

    override fun setColor(color: Color) {
    }
}