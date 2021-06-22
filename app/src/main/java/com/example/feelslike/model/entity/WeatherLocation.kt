package com.example.feelslike.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation(
    @SerializedName("q")
    val location : String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID
}