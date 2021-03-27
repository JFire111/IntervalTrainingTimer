package com.vinapp.intervaltrainingtimer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.vinapp.intervaltrainingtimer.databinding.FragmentIntervalKeyboardBinding
import com.vinapp.intervaltrainingtimer.mvp.view.IntervalKeyboardContract

class IntervalKeyboardView(val intervalKeyboardPresenter: IntervalKeyboardPresenter): Fragment(), IntervalKeyboardContract.View {

    private var _binding: FragmentIntervalKeyboardBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIntervalKeyboardBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        okButton = binding.saveIntervalButton
        cancelButton = binding.cancelIntervalButton

        okButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                intervalKeyboardPresenter.onSaveButtonClick(40)
            }
        })

        cancelButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                intervalKeyboardPresenter.onCancelButtonClick()
            }
        })

        return view
    }
}