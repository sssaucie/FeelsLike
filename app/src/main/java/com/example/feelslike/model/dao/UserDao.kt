package com.example.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.example.feelslike.model.entity.UserEntity

/**
 * Defines methods for using the UserEntity class with Room.
 */

@Dao

interface UserDao
{
    @Query("SELECT * FROM user_entity")
    fun loadAllUserInfo() : LiveData<List<UserEntity>>

    @Query("SELECT * FROM user_entity WHERE user_entity_id = :userId")
    fun loadUser(userId : Long) : UserEntity

    @Query("SELECT * FROM user_entity WHERE user_entity_id = :userId")
    fun loadLiveUserData(userId : Long) : LiveData<UserEntity>

    @Insert(onConflict = IGNORE)
    fun insertAllUserInfo(user : UserEntity) : Long

    @Insert(onConflict = IGNORE)
    fun insert(user : UserEntity)

    @Update(onConflict = REPLACE)
    fun update(user : UserEntity)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */

    @Query("DELETE FROM user_entity")
    suspend fun clear()
}