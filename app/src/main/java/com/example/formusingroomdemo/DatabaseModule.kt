package com.example.formusingroomdemo

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideUserDb(@ApplicationContext context: Context): UsersDatabase {
        return Room.databaseBuilder(context, UsersDatabase::class.java, "UserDb").fallbackToDestructiveMigration().build()

    }

    @Provides
    @Singleton
    fun provideUserDao(database: UsersDatabase): UserDao {
        return database.userDAo()
    }


}