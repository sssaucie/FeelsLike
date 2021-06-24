package com.example.feelslike.model.weather_service

import com.example.feelslike.model.entity.weather.WeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val baseUrl = "https://api.openweathermap.org"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(baseUrl)
    .build()

interface WeatherApiService
{
    @GET("/data/2.5/weather?")

    suspend fun searchWeatherByPlaceName(
        @Query("q") q : String,
        @Query("units") units : String,
        @Query("appid") appId : String) :
            WeatherResponse
}

object WeatherApi
{
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}