package com.example.feelslike.model.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feelslike.utilities.ONE_ID_ONLY
import java.io.Serializable

@Entity(tableName = "table_sys")
data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
): Serializable
{
    @PrimaryKey(autoGenerate = false)
    val oneId: Int = ONE_ID_ONLY
}