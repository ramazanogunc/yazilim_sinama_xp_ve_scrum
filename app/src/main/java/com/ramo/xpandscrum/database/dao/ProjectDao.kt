package com.ramo.xpandscrum.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ramo.xpandscrum.model.Project

/*
Proje sql sorgularını içerir.
 */
@Dao
interface ProjectDao {
    @Query("SELECT * FROM project__table")
    fun getAllProjects(): LiveData<List<Project>>

    @Query("SELECT * FROM project__table WHERE _id=:id")
    suspend fun getProject(id:Int): Project?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProject(project: Project)

    @Update
    suspend fun update(project: Project)

    @Query("DELETE FROM project__table WHERE _id=:id")
    suspend fun deleteProject(id:Int)
}
