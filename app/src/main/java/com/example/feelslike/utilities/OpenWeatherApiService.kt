package com.example.feelslike.utilities

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET

private const val BASE_URL = "api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={WEATHER_API_KEY}"
// TODO: Update URL with correct latlong coordinates based on user selection in Maps and
// TODO: include weather API key

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
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