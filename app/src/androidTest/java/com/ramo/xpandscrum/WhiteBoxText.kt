package com.ramo.xpandscrum

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.dao.ProjectDao
import com.ramo.xpandscrum.model.Project
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WhiteBoxText {

    private lateinit var projectDao: ProjectDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase.getInstance(context)
        projectDao = db.projectDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = runBlocking {
        val project: Project = Project("test")
        projectDao.insertProject(project)
        val allproject = projectDao.getAllTestProjects()
        assertThat(allproject.last().name, equalTo(project.name))
    }
}