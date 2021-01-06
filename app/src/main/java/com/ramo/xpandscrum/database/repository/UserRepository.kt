package com.ramo.xpandscrum.database.repository

import com.ramo.xpandscrum.database.dao.UserDao
import com.ramo.xpandscrum.model.User

class UserRepository(private val userDao: UserDao) {

    val allUsers = userDao.getAll()

    suspend fun insert(user: User) = userDao.insert(user)
}