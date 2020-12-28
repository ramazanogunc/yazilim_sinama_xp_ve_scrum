package com.ramo.xpandscrum.repository

import com.ramo.xpandscrum.database.dao.ProjectDao
import com.ramo.xpandscrum.model.Project
import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {

    val allProjects: Flow<List<Project>> = projectDao.getAllProjects()

    suspend fun insert(project: Project) {
        projectDao.insertProject(project)
    }


}