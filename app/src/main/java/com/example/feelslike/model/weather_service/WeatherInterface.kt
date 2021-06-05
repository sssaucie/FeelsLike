package com.example.feelslike.model.weather_service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface
{

    @GET("/data/2.5/weather?")

    suspend fun searchWeatherByPlaceName(
        @Query("q") q : String,
        @Query("appid") appId : String) :
            retrofit2.Response<WeatherAPI>

    companion object
    {
        private const val baseUrl = "https://api.openweathermap.org"

        val instance : WeatherInterface by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(WeatherInterface::class.java)
        }
    }
}