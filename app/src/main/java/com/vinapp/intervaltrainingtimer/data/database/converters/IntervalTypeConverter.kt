package com.vinapp.intervaltrainingtimer.data.database.converters

import androidx.room.TypeConverter
import com.vinapp.intervaltrainingtimer.entities.Interval
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class IntervalTypeConverter {

    @TypeConverter
    fun fromIntervalList(intervalList: List<Interval>): String {
        return Json.encodeToString(intervalList)
    }

    @TypeConverter
    fun toIntervalList(data: String): List<Interval> {
        return Json.decodeFromString<List<Interval>>(data)
    }
}