package com.vinapp.intervaltrainingtimer.ui.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.AddIntervalItemBinding

class TimerSettingsAdapter(private val intervalList: List<Any>): RecyclerView.Adapter<TimerSettingsAdapter.ViewHolder>() {

    class ViewHolder(val binding: AddIntervalItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddIntervalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return intervalList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
        }
    }
}