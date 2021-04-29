package com.example.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feelslike.model.entity.FeelsLikeEntity

/**
 * Defines methods for using the FeelsLikeEntity class with Room.
 */

@Dao
interface FeelsLikeDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll()

    @Query("SELECT * from feels_like")
    fun getAllNames() : LiveData<List<FeelsLikeEntity>>

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */

    @Query("DELETE FROM feels_like")
    suspend fun clear()
}