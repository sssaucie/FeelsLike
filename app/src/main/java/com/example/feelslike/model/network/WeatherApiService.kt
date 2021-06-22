package com.example.feelslike.model.network

import com.example.feelslike.R
import com.example.feelslike.model.entity.WeatherResponseEntity
import com.example.feelslike.model.network.ConnectivityInterceptor
import com.example.feelslike.model.weather_service.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val baseUrl = "https://api.openweathermap.org"

interface WeatherApiService
{
    @GET("/data/2.5/weather?")

    suspend fun searchWeatherByPlaceName(
        @Query("q") location : String,
        @Query("appid") appId : String,
        @Query("units") measurementUnits : String,
        @Query("lang") language : String) :
            Deferred<CurrentWeatherResponse>

    companion object
    {
        operator fun invoke(
            connectivityInterceptor : ConnectivityInterceptor
        ): WeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", R.string.WEATHER_API_KEY.toString())
                    .addQueryParameter("lang", "en")
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}