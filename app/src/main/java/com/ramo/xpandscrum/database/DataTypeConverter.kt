package com.ramo.xpandscrum.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ramo.xpandscrum.model.CardType
import java.lang.reflect.Type
import java.util.*


object DataTypeConverter {
    @TypeConverter
    @JvmStatic
    fun stringToCardType(value: String?): CardType? {
        if (value == null) {
            return null
        }
        val type: Type = object : TypeToken<CardType?>() {}.type
        return Gson().fromJson<CardType>(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun cardTypeToString(value: CardType?): String? {
        if (value == null) {
            return null
        }
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromTimesStamp(value: Long): Date = Date(value)

    @TypeConverter
    @JvmStatic
    fun fromDateToTimeStamp(value: Date): Long = value.time
}