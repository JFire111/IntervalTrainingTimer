package com.vinapp.intervaltrainingtimer.ui.sections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.TimerItemBinding
import com.vinapp.intervaltrainingtimer.entities.Timer

class TimerListAdapter(private val timerList: List<Timer>): RecyclerView.Adapter<TimerListAdapter.ViewHolder>() {

    class ViewHolder(val binding: TimerItemBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        override fun onClick(view: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TimerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return timerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            this.textView.text = timerList[position].name
        }
    }
}