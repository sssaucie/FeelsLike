package com.example.feelslike.model.entity

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi
{
    @GET("weather")
    suspend fun getWeather(@Query("lat") searchLatitude: Double?,
                           @Query("lon") searchLongitude: Double?,
                           @Query("appid") apiKey: String)
}