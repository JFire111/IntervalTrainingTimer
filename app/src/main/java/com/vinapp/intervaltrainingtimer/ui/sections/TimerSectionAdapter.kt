package com.vinapp.intervaltrainingtimer.ui.sections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.AddTimerItemBinding
import com.vinapp.intervaltrainingtimer.databinding.TimerItemBinding
import com.vinapp.intervaltrainingtimer.entities.Timer

class TimerSectionAdapter(private val timerList: ArrayList<Timer>, private val onTimerClickListener: OnTimerClickListener): RecyclerView.Adapter<TimerSectionAdapter.ViewHolder>() {

    private val TIMER_ITEM: Int = 0
    private val FOOTER_ITEM: Int  = 1

    interface OnTimerClickListener {
        fun onTimerClick(position: Int)
        fun onAddTimerClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == FOOTER_ITEM) {
            val binding = AddTimerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FooterViewHolder(binding, onTimerClickListener)
        } else {
            val binding = TimerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TimerItemViewHolder(binding, onTimerClickListener)
        }
    }

    override fun getItemCount(): Int {
        val footerItem = 1 //footerItem is additional list size for footer
        if (timerList.isNullOrEmpty())
            return footerItem
        else
            return timerList.size + footerItem
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TIMER_ITEM -> {
                var holder = viewHolder as TimerItemViewHolder
                with(holder.binding) {
                    this.textView.text = timerList[position].name
                }
            }
            FOOTER_ITEM -> {
                var holder = viewHolder as FooterViewHolder
                with(holder.binding) {
                    this.textView.text = "Add timer"
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < timerList.size) {
            return TIMER_ITEM
        } else {
            return FOOTER_ITEM
        }
    }

    class TimerItemViewHolder(val binding: TimerItemBinding, onTimerClickListener: OnTimerClickListener): ViewHolder(binding.root, onTimerClickListener) {
        override fun onClick(view: View?) {
            onTimerClickListener.onTimerClick(adapterPosition)
        }
    }

    class FooterViewHolder(val binding: AddTimerItemBinding, onTimerClickListener: OnTimerClickListener): ViewHolder(binding.root, onTimerClickListener) {
        override fun onClick(view: View?) {
            onTimerClickListener.onAddTimerClick()
        }
    }

    abstract class ViewHolder(itemView: View, val onTimerClickListener: OnTimerClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
    }
}