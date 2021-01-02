package com.ramo.xpandscrum.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ramo.xpandscrum.model.BoardType
import java.lang.reflect.Type
import java.util.*


object DataTypeConverter {
    @TypeConverter
    @JvmStatic
    fun stringToBoardType(value: String?): BoardType? {
        if (value == null) {
            return null
        }
        val type: Type = object : TypeToken<BoardType?>() {}.type
        return Gson().fromJson<BoardType>(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun boardTypeToString(value: BoardType?): String? {
        if (value == null) {
            return null
        }
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromTimesStamp(value: Long): Date = Date(value)

    @TypeConverter
    fun fromDateToTimeStamp(value: Date): Long = value.time
}