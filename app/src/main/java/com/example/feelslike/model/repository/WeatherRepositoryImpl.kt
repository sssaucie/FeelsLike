package com.example.feelslike.model.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.feelslike.model.dao.WeatherDao
import com.example.feelslike.model.entity.WeatherResponseEntity
import com.example.feelslike.model.network.WeatherNetworkDataSource
import com.example.feelslike.model.weather_service.StandardUnitWeatherResponseEntry
import com.example.feelslike.model.weather_service.response.CurrentWeatherResponse
import kotlinx.coroutines.*
import java.time.ZonedDateTime
import java.util.*

@DelicateCoroutinesApi
class WeatherRepositoryImpl(
    private val weatherDao : WeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherRepository {

    init {
        weatherNetworkDataSource.downloadedWeather.observeForever { newWeather ->
            persistFetchedCurrentWeather(newWeather)
        }
    }
    override suspend fun getWeather(metric: Boolean): LiveData<out StandardUnitWeatherResponseEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) weatherDao.getWeatherMetric()
            else weatherDao.getWeatherImperial()
        }
    }

    @DelicateCoroutinesApi
    private fun persistFetchedCurrentWeather(fetchedWeather : CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            "Phoenix",
            Locale.getDefault().language
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime) : Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}