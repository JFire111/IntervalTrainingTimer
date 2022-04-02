package com.vinapp.intervaltrainingtimer.ui.interval_keyboard

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.common.IntervalType
import com.vinapp.intervaltrainingtimer.databinding.FragmentKeyboardBinding
import com.vinapp.intervaltrainingtimer.mvp.view.IntervalKeyboardContract

class IntervalKeyboardView(private val intervalKeyboardPresenter: IntervalKeyboardPresenter): Fragment(), IntervalKeyboardContract.View {

    private var _binding: FragmentKeyboardBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var intervalNameTextView: EditText
    private lateinit var displayTextView: TextView
    private lateinit var keyboardGridLayout: GridLayout
    private lateinit var restButton: Button
    private lateinit var workButton: Button
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button
    private lateinit var deleteButton: FloatingActionButton

    private val keyboardLength = 10

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKeyboardBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        initIntervalNameTextView()
        displayTextView = binding.displayTextView
        initKeyboardGridLayout()
        initRestButton()
        initWorkButton()
        initOkButton()
        initCancelButton()
        initDeleteButton()
        return view
    }

    override fun showIntervalName(name: String) {
        intervalNameTextView.setText(name)
    }

    override fun getIntervalName(): String {
        return intervalNameTextView.text.toString()
    }

    override fun showTimeValue(timeValue: String, valuesArray: Array<Int?>) {
        displayTextView.text = textColoring(timeValue, valuesArray)
    }

    override fun showSelectedType(intervalType: IntervalType) {
        when(intervalType) {
            IntervalType.REST -> {
                restButton.setTextColor(ContextCompat.getColor(context!!, R.color.green))
                workButton.setTextColor(ContextCompat.getColor(context!!, R.color.inactiveGray))
            }
            IntervalType.WORK -> {
                restButton.setTextColor(ContextCompat.getColor(context!!, R.color.inactiveGray))
                workButton.setTextColor(ContextCompat.getColor(context!!, R.color.red))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        intervalKeyboardPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        intervalKeyboardPresenter.detachView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        intervalKeyboardPresenter.destroy()
    }

    private fun initIntervalNameTextView() {
        intervalNameTextView = binding.intervalNameEditText
        binding.root.setOnClickListener {
            intervalNameTextView.clearFocus()
        }
        intervalNameTextView.setOnFocusChangeListener { view, hasFocus ->
            (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
    }

    private fun initKeyboardGridLayout() {
        keyboardGridLayout = binding.keyboardGridLayout
        for (index in 0 until keyboardLength) {
            keyboardGridLayout[index].setOnClickListener {
                intervalNameTextView.clearFocus()
                if (index == 9) {
                    intervalKeyboardPresenter.onKeyboardButtonClick(0)
                } else {
                    intervalKeyboardPresenter.onKeyboardButtonClick(index + 1)
                }
            }
        }
    }

    private fun initRestButton() {
        restButton = binding.keyboardButtonRest
        restButton.setOnClickListener {
            intervalNameTextView.clearFocus()
            intervalKeyboardPresenter.onRestButtonClick()
        }
    }

    private fun initWorkButton() {
        workButton = binding.keyboardButtonWork
        workButton.setOnClickListener {
            intervalNameTextView.clearFocus()
            intervalKeyboardPresenter.onWorkButtonClick()
        }
    }

    private fun initOkButton() {
        okButton = binding.keyboardButtonOk
        okButton.setOnClickListener {
            intervalKeyboardPresenter.onOkButtonClick()
        }
    }

    private fun initCancelButton() {
        cancelButton = binding.keyboardButtonCancel
        cancelButton.setOnClickListener {
            intervalKeyboardPresenter.onCancelButtonClick()
        }
    }

    private fun initDeleteButton() {
        deleteButton = binding.keyboardButtonDelete
        deleteButton.setOnClickListener {
            intervalKeyboardPresenter.onDeleteButtonClick()
        }
    }

    private fun textColoring(string: String, coloredSymbolsArray: Array<Int?>): SpannableString {
        val spannableString = SpannableString(string)
        for ((index, value) in coloredSymbolsArray.withIndex()) {
            if (value != null) {
                if (index < string.length / 2) {
                    spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.red)), index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                } else {
                    spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.red)), index + 1, index + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
        return spannableString
    }
}