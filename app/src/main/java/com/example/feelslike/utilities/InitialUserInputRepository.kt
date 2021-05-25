package com.example.feelslike.utilities

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.feelslike.model.dao.UserDao
import com.example.feelslike.model.entity.UserEntity

class InitialUserInputRepository(private val userDao : UserDao)
{
    val allUserInput : LiveData<List<UserEntity>> = userDao.getAllUserInfo()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user : UserEntity)
    {
        userDao.insert(user)
    }
}