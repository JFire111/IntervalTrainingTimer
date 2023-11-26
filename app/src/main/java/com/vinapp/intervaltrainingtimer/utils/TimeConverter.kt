package com.vinapp.intervaltrainingtimer.utils

import com.vinapp.intervaltrainingtimer.domain.Interval
import com.vinapp.intervaltrainingtimer.ui_components.time_text.TimeDigits

object TimeConverter {
    fun getTimeString(timeInSeconds: Long): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds - minutes * 60
        return "${minutes / 10}${minutes % 10}:${seconds / 10}${seconds % 10}"
    }

    fun getTimeString(numberOfRounds: Int, intervalList: List<Interval>, timeBetweenRounds: Int?): String {
        return getTimeString(
            getTimeLong(
                numberOfRounds = numberOfRounds,
                intervalList = intervalList,
                timeBetweenRounds = timeBetweenRounds
            )
        )
    }

    fun getTimeDigits(timeInSeconds: Long): TimeDigits {
        val minutes = (timeInSeconds / 60).toInt()
        val seconds = (timeInSeconds - minutes * 60).toInt()
        return TimeDigits(
            first = minutes / 10,
            second = minutes % 10,
            third = seconds / 10,
            fourth = seconds % 10
        )
    }

    fun getTimeDigits(numberOfRounds: Int, intervalList: List<Interval>, timeBetweenRounds: Int?): TimeDigits {
        return getTimeDigits(
            getTimeLong(
                numberOfRounds = numberOfRounds,
                intervalList = intervalList,
                timeBetweenRounds = timeBetweenRounds
            )
        )
    }

    fun getTimeLong(timeDigits: TimeDigits): Long {
        return ((timeDigits.first ?: 0) * 600 +
                (timeDigits.second ?: 0) * 60 +
                (timeDigits.third ?: 0) * 10 +
                (timeDigits.fourth ?: 0)).toLong()
    }

    fun getTimeLong(numberOfRounds: Int, intervalList: List<Interval>, timeBetweenRounds: Int?): Long {
        val timeBetweenAllRounds = if (intervalList.isNotEmpty() && timeBetweenRounds != null) {
            timeBetweenRounds * (numberOfRounds - 1)
        } else {
            0
        }
        return intervalList.sumOf { interval ->
            interval.duration
        } * numberOfRounds + timeBetweenAllRounds
    }
}