package com.example.feelslike.model.repository

import androidx.lifecycle.LiveData
import com.example.feelslike.model.weather_service.StandardUnitWeatherResponseEntry

interface WeatherRepository {
    suspend fun getWeather(metric : Boolean) : LiveData<out StandardUnitWeatherResponseEntry>
}