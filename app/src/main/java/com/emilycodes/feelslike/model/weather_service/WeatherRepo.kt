package com.emilycodes.feelslike.model.weather_service

import com.emilycodes.feelslike.BuildConfig

class WeatherRepo(private val weatherInterface : WeatherApiService)
{
    val appId = BuildConfig.WEATHER_API_KEY
    suspend fun searchByPlace(placeName : String, unit : String, appId : String) =
        weatherInterface.searchWeatherByPlaceName(placeName, unit, appId)
}