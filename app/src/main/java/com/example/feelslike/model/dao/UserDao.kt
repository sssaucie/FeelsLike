package com.example.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.feelslike.model.entity.UserEntity

/**
 * Defines methods for using the UserEntity class with Room.
 */

@Dao

interface UserDao
{
    @Query("SELECT * FROM user_entity")
    fun getAllUserInfo() : LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllUserInfo(user: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */

    @Query("DELETE FROM user_entity")
    suspend fun clear()
}