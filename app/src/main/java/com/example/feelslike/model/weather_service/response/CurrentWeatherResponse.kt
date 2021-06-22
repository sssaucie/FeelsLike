package com.example.feelslike.model.weather_service.response

import com.example.feelslike.model.entity.WeatherLocation
import com.example.feelslike.model.entity.WeatherResponseEntity
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val location : WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry : WeatherResponseEntity
)