package com.example.formusingroomdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users (
    @PrimaryKey(autoGenerate = true) val id :Int = 0,
    @ColumnInfo(name = "UserName") val userName : String,
    @ColumnInfo(name = "Email")val email :String,
    @ColumnInfo(name = "Password")val password : String,
    @ColumnInfo(name = "Contact") val contact :String,
    @ColumnInfo(name= "gender") val gender : String,
    @ColumnInfo(name = "image") val image : String? = null,
    @ColumnInfo(name = "hobbies")val hobbies :String

)