package com.ramo.xpandscrum.database

import androidx.room.TypeConverter
import com.ramo.xpandscrum.model.BoardType

class BoardTypeConverter {

    @TypeConverter
    fun fromBoardType(boardType: BoardType): String {
        return boardType.name
    }

    @TypeConverter
    fun toPriority(name: String): BoardType {
        return BoardType.valueOf(name)
    }

}