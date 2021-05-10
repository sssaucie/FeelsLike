package com.example.feelslike.utilities

import com.example.feelslike.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// TODO: Add Weather Dao (ask Brad for example if stuck)
// TODO: Add Weather Entities and Lists
// TODO: Decide whether or not to bulk download vs. latlong selected location live

private const val BASE_URL = "api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={WEATHER_API_KEY}"
private const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY
// TODO: Update URL with correct latlong coordinates based on user selection in Maps and
// TODO: include weather API key

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface OpenWeatherApiService
{
    @GET("weather")
    fun getProperties() :
            Call<String>
}

object OpenWeatherApi
{
    val retrofitService : OpenWeatherApiService by lazy {
        retrofit.create(OpenWeatherApiService::class.java)
    }
}