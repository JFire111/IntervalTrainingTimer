package com.vinapp.intervaltrainingtimer.data.database.converters

import androidx.room.TypeConverter
import com.vinapp.intervaltrainingtimer.common.IntervalColor

class IntervalColorConverter {

    @TypeConverter
    fun toIntervalColor(value: String) = enumValueOf<IntervalColor>(value)

    @TypeConverter
    fun fromIntervalColor(value: IntervalColor) = value.name
}