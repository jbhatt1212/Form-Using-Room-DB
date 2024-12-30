package com.example.formusingroomdemo

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val userDao: UserDao) : UsersRepository {
    override suspend fun insertOrUpdate(user: Users) {
        return userDao.insertOrUpdate(user)
    }

    override fun getAllUsers(): LiveData<List<Users>> {
        return userDao.getAllUsers()
    }
    override suspend fun getUserByEmail(email: String): Users? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByEmail(email)
        }
    }
    override suspend fun getUserById(id: Int): Users? {
        return userDao.getUserById(id)
    }

    override suspend fun addUser(user: Users) {
        userDao.insertOrUpdate(user)
    }
}