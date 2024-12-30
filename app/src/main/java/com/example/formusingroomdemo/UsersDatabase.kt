package com.example.formusingroomdemo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Users::class], version = 1, exportSchema = false)

abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDAo(): UserDao
}