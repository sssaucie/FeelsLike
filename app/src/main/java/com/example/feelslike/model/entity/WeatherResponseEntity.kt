package com.example.feelslike.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feelslike.model.weather_service.response.*

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "weather_response")
data class WeatherResponseEntity(
    val base: String,
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    val cod: Int,
    @Embedded(prefix = "coord_")
    val coord: Coord,
    val dt: Int,
    val id: Int,
    @Embedded(prefix = "main_")
    val main: Main,
    val name: String,
    @Embedded(prefix = "sys_")
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    @Embedded(prefix = "list_weather_")
    val weather: List<Weather>,
    @Embedded(prefix = "wind_")
    val wind: Wind
) {
    @PrimaryKey(autoGenerate = false)
    var idInternal : Int = CURRENT_WEATHER_ID
}