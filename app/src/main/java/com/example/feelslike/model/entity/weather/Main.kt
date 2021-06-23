package com.example.feelslike.model.entity.weather

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feelslike.utilities.ONE_ID_ONLY
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
): Serializable