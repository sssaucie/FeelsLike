package com.emilycodes.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.emilycodes.feelslike.model.entity.CalculationsEntity

@Dao
interface CalculationsDao
{
    @Query("SELECT * FROM calculations_entity")
    fun loadAllCalculationsInfo() : LiveData<List<CalculationsEntity>>

    @Query("SELECT * FROM calculations_entity WHERE calculations_entity_id = :calculationsId")
    fun loadCalculations(calculationsId : Long) : CalculationsEntity

    @Query("SELECT * FROM calculations_entity WHERE calculations_entity_id = :calculationsId")
    fun loadLiveCalculations(calculationsId : Long) : LiveData<CalculationsEntity>

    @Insert(onConflict = IGNORE)
    fun insertAllCalculationsInfo(calculations: CalculationsEntity) : Long

    @Update(onConflict = IGNORE)
    fun updateCalculationsInfo(calculations: CalculationsEntity)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */

    @Query("DELETE FROM calculations_entity")
    suspend fun clear()
}