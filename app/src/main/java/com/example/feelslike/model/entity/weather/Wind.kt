package com.example.feelslike.model.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feelslike.utilities.ONE_ID_ONLY
import java.io.Serializable

@Entity
data class Wind(
    val deg: Int,
    val speed: Int
): Serializable
{
    @PrimaryKey(autoGenerate = false)
    val oneId : Int = ONE_ID_ONLY
}