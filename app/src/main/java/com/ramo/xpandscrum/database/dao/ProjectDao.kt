package com.ramo.xpandscrum.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramo.xpandscrum.model.Project

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project")
    fun getAllProjects(): LiveData<List<Project>>

    @Query("SELECT * FROM project WHERE id=:id")
    suspend fun getProject(id:Int): Project?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProject(project: Project)

    @Query("DELETE FROM project WHERE id=:id")
    fun deleteProject(id:Int)
}