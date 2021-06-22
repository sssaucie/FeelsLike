package com.example.feelslike.model.network

import androidx.lifecycle.LiveData
import com.example.feelslike.model.entity.WeatherResponseEntity

interface WeatherNetworkDataSource {
    val downloadedWeather : LiveData<WeatherResponseEntity>

    suspend fun fetchCurrentWeather(
        location : String,
        languageCode : String
    )
}