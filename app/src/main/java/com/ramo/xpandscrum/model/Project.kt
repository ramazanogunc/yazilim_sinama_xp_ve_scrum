package com.ramo.xpandscrum.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project__table")
data class Project(
    @ColumnInfo(name = "name") val name: String
){
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0
}
