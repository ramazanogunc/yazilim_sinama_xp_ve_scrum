package com.ramo.xpandscrum.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
kullanıcı bilgilerini içeren model
aynı zamanda veritabanı için de kullanılır
 */
@Entity(tableName = "user_table")
data class User(
    val name: String
){
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0
}
