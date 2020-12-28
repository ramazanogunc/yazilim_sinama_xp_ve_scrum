package com.ramo.xpandscrum.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramo.xpandscrum.model.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project")
    fun getAllProjects(): Flow<List<Project>>

    @Query("SELECT * FROM project WHERE id=:id")
    suspend fun getProject(id:Int): Project

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProject(project: Project)

    @Query("DELETE FROM project WHERE id=:id")
    fun deleteProject(id:Int)
}
