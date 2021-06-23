package com.example.feelslike.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feelslike.model.entity.weather.*
import kotlinx.parcelize.Parcelize

private const val ONE_ID_ONLY = 0
@Entity(tableName = "weather_results")
@Parcelize
data class WeatherEntity(
    @PrimaryKey val oneId: Int = ONE_ID_ONLY,
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) : Parcelable