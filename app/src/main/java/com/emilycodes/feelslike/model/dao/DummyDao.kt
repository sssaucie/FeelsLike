package com.emilycodes.feelslike.model.dao

import androidx.room.Dao
import androidx.room.Insert
import com.emilycodes.feelslike.model.entity.Dummy

@Dao
interface DummyDao
{
    @Insert
    suspend fun insert(dummy: Dummy)
}