package com.example.formusingroomdemo

import androidx.lifecycle.LiveData

interface UsersRepository {

    suspend fun insertOrUpdate(user: Users)
    fun getAllUsers(): LiveData<List<Users>>
    suspend fun getUserByEmail(email: String): Users?
    suspend fun getUserById(id: Int): Users?

    suspend fun addUser(user: Users)
}