package com.example.formusingroomdemo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: Users)

    @Query("SELECT * FROM users WHERE Email = :email")
    fun getUserByEmail(email : String): Users?

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<Users>>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): Users?
}