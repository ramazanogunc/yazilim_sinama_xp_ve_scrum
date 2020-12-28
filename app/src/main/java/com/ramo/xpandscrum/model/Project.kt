package com.ramo.xpandscrum.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Project(
    @ColumnInfo(name = "name") val name: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
