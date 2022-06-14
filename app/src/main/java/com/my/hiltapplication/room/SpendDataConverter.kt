package com.my.hiltapplication.room

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by YourName on 2022/06/14.
 */
class SpendDataConverter {
    @TypeConverter
    fun toDate(timeMill: Long): Date {
        return Date(timeMill)
    }

    @TypeConverter
    fun toTimeMill(date:Date): Long {
        return date.time
    }
}