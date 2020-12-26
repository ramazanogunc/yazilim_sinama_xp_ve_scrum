package com.ramo.xpandscrum.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ramo.xpandscrum.model.Project

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project")
    fun getAll(): List<Project>

    @Query("SELECT * FROM project WHERE id=:id")
    fun get(id:Int): Project

    @Insert
    fun insert(project: Project)

    @Query("DELETE FROM project WHERE id=:id")
    fun delete(id:Int)

}
