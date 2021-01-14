package com.ramo.xpandscrum.database.repository

import com.ramo.xpandscrum.database.dao.ProjectDao
import com.ramo.xpandscrum.model.Project

/*
veritabanı ile viewmodel arasındakı işlemleri yapar.
 */
class ProjectRepository(private val projectDao: ProjectDao) {

    val allProjects = projectDao.getAllProjects()

    suspend fun insert(project: Project) {
        projectDao.insertProject(project)
    }

    suspend fun get(id: Int) = projectDao.getProject(id)

    suspend fun update(project: Project) = projectDao.update(project)

    suspend fun delete(id : Int) = projectDao.deleteProject(id)


}