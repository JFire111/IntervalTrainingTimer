package com.vinapp.intervaltrainingtimer.ui.sections

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.databinding.AddTimerItemBinding
import com.vinapp.intervaltrainingtimer.databinding.TimerItemBinding
import com.vinapp.intervaltrainingtimer.entities.Timer

class TimersSectionAdapter(private val timerList: List<Timer>, private val onTimerClickListener: OnTimerClickListener): RecyclerView.Adapter<TimersSectionAdapter.ViewHolder>() {

    private val TIMER_ITEM: Int = 0
    private val FOOTER_ITEM: Int  = 1
    private var selected: Int? = null

    interface OnTimerClickListener {
        fun onTimerClick(position: Int)
        fun onAddTimerClick()
        fun onDeleteClickListener(position: Int)
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
                    this.timerNameTextView.text = timerList[position].id.toString()
                    this.timerDurationTextView.text = timerList[position].getDurationAsString()
                    if (selected == position) {
                        this.deleteTimerButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.root.context, R.color.lightGray))
                        this.root.setBackgroundColor(ContextCompat.getColor(this.root.context, R.color.lightGray))
                    } else {
                        this.deleteTimerButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.root.context, R.color.darkGray))
                        this.root.setBackgroundColor(ContextCompat.getColor(this.root.context, R.color.darkGray))
                    }
                }
            }
            FOOTER_ITEM -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < timerList.size) {
            return TIMER_ITEM
        } else {
            return FOOTER_ITEM
        }
    }

    inner class TimerItemViewHolder(val binding: TimerItemBinding, onTimerClickListener: OnTimerClickListener): ViewHolder(binding.root, onTimerClickListener) {
        init {
            binding.deleteTimerButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    onTimerClickListener.onDeleteClickListener(adapterPosition)
                }
            })
        }
        override fun onClick(view: View?) {
            val previousSelected = selected
            if (selected == adapterPosition) {
                selected = null
            } else {
                selected = adapterPosition
            }
            onTimerClickListener.onTimerClick(adapterPosition)
            if (previousSelected != null) {
                notifyItemChanged(previousSelected)
            }
            notifyItemChanged(adapterPosition)
        }
    }

    inner class FooterViewHolder(val binding: AddTimerItemBinding, onTimerClickListener: OnTimerClickListener): ViewHolder(binding.root, onTimerClickListener) {
        override fun onClick(view: View?) {
            selected = null
            onTimerClickListener.onAddTimerClick()
        }
    }

    abstract class ViewHolder(itemView: View, val onTimerClickListener: OnTimerClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
    }
}