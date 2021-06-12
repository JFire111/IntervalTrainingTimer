package com.vinapp.intervaltrainingtimer.ui.sections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinapp.intervaltrainingtimer.databinding.AddIntervalItemBinding
import com.vinapp.intervaltrainingtimer.databinding.IntervalItemBinding
import com.vinapp.intervaltrainingtimer.entities.base.Interval

class IntervalsSectionAdapter(private val intervalList: ArrayList<Interval>, private val onIntervalClickListener: OnIntervalClickListener): RecyclerView.Adapter<IntervalsSectionAdapter.ViewHolder>() {

    private val INTERVAL_ITEM: Int  = 0
    private val FOOTER_ITEM: Int  = 1

    interface OnIntervalClickListener {
        fun onIntervalClick(position: Int)
        fun onAddIntervalClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == FOOTER_ITEM) {
            val binding = AddIntervalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FooterViewHolder(binding, onIntervalClickListener)
        } else {
            val binding = IntervalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return IntervalItemViewHolder(binding, onIntervalClickListener)
        }
    }

    override fun getItemCount(): Int {
        val footerItem = 1 //footerItem is additional list size for footer
        if (intervalList.isNullOrEmpty())
            return footerItem
        else
            return intervalList.size + footerItem
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            INTERVAL_ITEM -> {
            var holder = viewHolder as IntervalItemViewHolder
                with(holder.binding) {
                    this.intervalNameTextView.text = intervalList[position].name
                    this.intervalTimeTextView.text = intervalList[position].getDurationAsString()
                }
            }
            FOOTER_ITEM -> {
                var holder = viewHolder as FooterViewHolder
                with(holder.binding) {
                    this.textView.text = "Add interval"
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < intervalList.size) {
            return INTERVAL_ITEM
        } else {
            return FOOTER_ITEM
        }
    }

    class IntervalItemViewHolder(val binding: IntervalItemBinding, onIntervalClickListener: OnIntervalClickListener): ViewHolder(binding.root, onIntervalClickListener) {
        override fun onClick(view: View?) {
            onIntervalClickListener.onIntervalClick(adapterPosition)
        }
    }

    class FooterViewHolder(val binding: AddIntervalItemBinding, onIntervalClickListener: OnIntervalClickListener): ViewHolder(binding.root, onIntervalClickListener) {
        override fun onClick(view: View?) {
            onIntervalClickListener.onAddIntervalClick()
        }
    }

    abstract class ViewHolder(itemView: View, val onIntervalClickListener: OnIntervalClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
    }
}