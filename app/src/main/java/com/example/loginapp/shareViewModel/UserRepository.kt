package com.example.loginapp.shareViewModel

import androidx.lifecycle.LiveData
import com.example.loginapp.db.User
import com.example.loginapp.db.UserDao

class UserRepository(private val userDao: UserDao) {

    val allUser: LiveData<List<User>> = userDao.getUser()

     fun getUserById(userId: Int): LiveData<User> {
      return  userDao.getUserById(userId)
    }

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }
}