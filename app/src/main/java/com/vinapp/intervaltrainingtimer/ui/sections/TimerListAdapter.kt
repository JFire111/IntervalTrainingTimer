package com.vinapp.intervaltrainingtimer.ui.sections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.TimerItemBinding
import com.vinapp.intervaltrainingtimer.entities.Timer

class TimerListAdapter(private val timerList: List<Timer>, private val onTimerListener: OnTimerListener): RecyclerView.Adapter<TimerListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TimerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onTimerListener)
    }

    override fun getItemCount(): Int {
        return timerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            this.textView.text = timerList[position].name
        }
    }

    class ViewHolder(val binding: TimerItemBinding, val onTimerListener: OnTimerListener): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onTimerListener.onTimerClick(adapterPosition)
        }
    }

    interface OnTimerListener {
        fun onTimerClick(position: Int)
    }
}