package com.vinapp.intervaltrainingtimer.ui_components.timer

import com.vinapp.intervaltrainingtimer.utils.TimeConverter

interface TimeToStringConverter {
    fun convert(time: Long): String
}

/**
 * Converter for "MM:SS" string format from milliseconds
 */
class DefaultTimeToStringConverter : TimeToStringConverter {
    override fun convert(time: Long): String {
        val timeInSeconds = TimeConverter.getTimeInSeconds(time)
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds - minutes * 60
        println("${minutes / 10}${minutes % 10}:${seconds / 10}${seconds % 10}")
        return "${minutes / 10}${minutes % 10}:${seconds / 10}${seconds % 10}"
    }
}