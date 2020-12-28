package com.ramo.xpandscrum.database.repository

import com.ramo.xpandscrum.database.dao.ProjectDao
import com.ramo.xpandscrum.model.Project

class ProjectRepository(private val projectDao: ProjectDao) {

    val allProjects = projectDao.getAllProjects()

    suspend fun insert(project: Project) {
        projectDao.insertProject(project)
    }

    suspend fun get(id: Int) = projectDao.getProject(id)


}