package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.feelslike.BuildConfig
import com.example.feelslike.R
import com.example.feelslike.model.weather_service.WeatherResponse
import com.example.feelslike.utilities.WeatherRepo

class ResultsViewModel(weatherResponse : WeatherResponse, application : Application) : AndroidViewModel(application)
{
    private val TAG = ResultsViewModel::class.java.simpleName
    private var weatherRepo : WeatherRepo? = null


    private val _selectedLocation = MutableLiveData<WeatherResponse>()
    val selectedLocation : LiveData<WeatherResponse>
        get() = _selectedLocation

    init
    {
        _selectedLocation.value = weatherResponse
    }

    // Trying to recreate what I did in MarsRealEstate App assignment
    val retrieveLocationCoordinates = Transformations.map(selectedLocation)
    {
        application.applicationContext.getText(
            when (it.coord.hasCoordinates != null)
            {
                true -> weatherResponse.coord.hasCoordinates
                false -> Log.i(TAG, "No coordinates found")
                else -> Log.e(TAG, "Error retrieving coordinates")
            })
    }

    val displayWeatherData = Transformations.map(selectedLocation)
    {
        application.applicationContext.resources(R.id.results_temp)
    }

    /**
     * All the following code is derived from my Android book
     */
    // Collection of default data necessary for the View
    data class WeatherSummaryViewData(
        var latitude : Double = 0.0,
        var longitude : Double = 0.0,
        var temp : Float? = 0.0F,
        var feelsLikeTemp : Float? = 0.0F,
        var baroPressure : Int = 0,
        var humidity : Int = 0)

    // Helper method converting raw model data to view data
    private fun weatherResponseToWeatherSummaryView(
        coordinates : WeatherResponse.Coordinates,
        weather : WeatherResponse.BaseWeatherStats
    ) : WeatherSummaryViewData
    {
        return WeatherSummaryViewData(
            coordinates.lat,
            coordinates.lon,
            weather.temp,
            weather.feels_like,
            weather.pressure,
            weather.humidity
        )
    }

    // This gets called by the Activity performing the search
    suspend fun searchTemperature(location : String) : List<WeatherSummaryViewData>
    {
        val results = weatherRepo?.searchByPlace(location, BuildConfig.WEATHER_API_KEY)

        if (results != null && results.isSuccessful)
        {
            val coordinates = results.body()?.coord
            val baseWeatherStats = results.body()?.main

            // TODO : Ask Brad for help - How to .map these data class responses
            //  and check for not null or empty

            if (!coordinates.isNullOrEmpty())
            {
                if (!baseWeatherStats.isNullOrEmpty())
                {
                    return baseWeatherStats.map { base ->
                        weatherResponseToWeatherSummaryView(coordinates, base)
                    }
                }
                return coordinates.map { latLng ->
                    weatherResponseToWeatherSummaryView(latLng, baseWeatherStats)
                }
            }
        }
        return emptyList()
    }
}