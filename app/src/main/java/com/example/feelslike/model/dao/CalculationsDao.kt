package com.example.feelslike.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feelslike.model.entity.CalculationsEntity

@Dao
interface CalculationsDao
{
    @Query("SELECT * FROM calculations_entity")
    fun getAllCalculationsInfo() : List<CalculationsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCalculationsInfo(calculations: List<CalculationsEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(calculations: CalculationsEntity)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */

    @Query("DELETE FROM calculations_entity")
    suspend fun clear()
}