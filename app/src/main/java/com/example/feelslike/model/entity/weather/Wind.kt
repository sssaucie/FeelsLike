package com.example.feelslike.model.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feelslike.utilities.ONE_ID_ONLY
import java.io.Serializable

data class Wind(
    val deg: Int,
    val speed: Double
): Serializable
