package com.example.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.feelslike.model.entity.FeelsLikeEntity

/**
 * Defines methods for using the FeelsLikeEntity class with Room.
 */

@Dao

interface FeelsLikeDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(user: List<FeelsLikeEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user:FeelsLikeEntity)

    @Update
    suspend fun update(user: FeelsLikeEntity)

    @Query("SELECT * from feels_like")
    fun getUserInfo() : LiveData<List<FeelsLikeEntity>>

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */

    @Query("DELETE FROM feels_like")
    suspend fun clear()
}