package com.example.feelslike.model.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.feelslike.model.entity.Dummy

@Dao
interface DummyDao
{
    @Insert
    suspend fun insert(dummy: Dummy)
}