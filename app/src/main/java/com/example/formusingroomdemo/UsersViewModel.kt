package com.example.formusingroomdemo

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UsersViewModel@Inject constructor(private val userRepository: UsersRepository) : ViewModel() {


    val users: LiveData<List<Users>> = userRepository.getAllUsers()

    fun addUser(userName: String, email: String, password: String, contact: String, gender: String,image : Bitmap?,hobbies :List<String>) {
        viewModelScope.launch {
            val imageBase64 = image?.let { ImageUtils.bitmapToBase64(it) }
            val hobbiesString = hobbies.joinToString(",")

            val existingUser = userRepository.getUserByEmail(email)

            if (existingUser != null) {
                val updatedUser = existingUser.copy(userName = userName, password = password, contact = contact, gender = gender, image = imageBase64, hobbies = hobbiesString)
                userRepository.insertOrUpdate(updatedUser)

            } else {
                val user = Users(userName = userName, email = email, password = password, contact = contact, gender = gender, image = imageBase64, hobbies = hobbiesString)
                userRepository.insertOrUpdate(user)
            }
        }
    }


    fun fetchAllUsers(onResult: (LiveData<List<Users>>) -> Unit) {
        viewModelScope.launch {
            val users = userRepository.getAllUsers()
            onResult(users)
        }
    }

    fun fetchUserById(id: Int, onResult: (Users?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserById(id)
            onResult(user)
        }
    }
}