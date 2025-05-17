package com.example.loginapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table ORDER BY username ASC")
    fun getUser():LiveData<List<User>>


    @Query("SELECT * FROM user_table WHERE id = :userId LIMIT 1 ")
    fun getUserById(userId:Int):LiveData<User>

    @Delete
    suspend fun delete(user: User)
}