package com.ramo.xpandscrum.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ramo.xpandscrum.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAll(): LiveData<List<User>>

    @Insert
    suspend fun insert(user: User)
}