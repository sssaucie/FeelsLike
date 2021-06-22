package com.example.feelslike.model.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.BuildConfig.WEATHER_API_KEY
import com.example.feelslike.model.entity.WeatherResponseEntity
import com.example.feelslike.utilities.NoConnectivityException

class WeatherNetworkDataSourceImpl(private val apiService: WeatherApiService) : WeatherNetworkDataSource {
    private val TAG = WeatherNetworkDataSourceImpl::class.java.simpleName
    private val _downloadedWeather = MutableLiveData<WeatherResponseEntity>()

    override val downloadedWeather: LiveData<WeatherResponseEntity>
        get() = _downloadedWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = apiService
                .searchWeatherByPlaceName(location, WEATHER_API_KEY, "", languageCode)
                .await()
            _downloadedWeather.postValue(fetchedCurrentWeather)
        }
        catch(e: NoConnectivityException) {
            Log.e(TAG, "No internet connection. Error: $e")
        }
    }
}