package com.example.loginapp.shareViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.db.AppDatabase
import com.example.loginapp.db.User
import kotlinx.coroutines.launch

class LoginViewModel (application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    private var repository = UserRepository(userDao)
    val allUser:LiveData<List<User>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUser = repository.allUser
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }
    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }
    fun getUserById(userId:Int):LiveData<User>{
        return repository.getUserById(userId)
    }
}