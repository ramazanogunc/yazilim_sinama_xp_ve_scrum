package com.ramo.xpandscrum.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Project(
    @ColumnInfo(name = "first_name") val name: String
){
    @PrimaryKey(autoGenerate = true) val id: Int? = null
}
