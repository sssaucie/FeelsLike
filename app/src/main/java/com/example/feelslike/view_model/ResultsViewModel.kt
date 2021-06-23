package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.BuildConfig
import com.example.feelslike.model.entity.Coord
import com.example.feelslike.model.entity.Main
import com.example.feelslike.model.entity.WeatherEntity
import com.example.feelslike.model.weather_service.WeatherRepo

class ResultsViewModel(application : Application) : AndroidViewModel(application)
{
    private val TAG = ResultsViewModel::class.java.simpleName
    private var weatherRepo : WeatherRepo? = null

    lateinit var weatherSummaryViewData: MutableLiveData<WeatherSummaryViewData>

    private val _navigateToLandingPage = MutableLiveData<Boolean?>()
    private val _selectedLocation = MutableLiveData<WeatherEntity>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage
    val selectedLocation : LiveData<WeatherEntity>
        get() = _selectedLocation

    fun onSearchAgainClicked()
    {
        _navigateToLandingPage.value = true
        Log.i(TAG, "Search Again clicked")
    }

    fun onNavigated()
    {
        _navigateToLandingPage.value = null
    }

//    init
//    {
//        _selectedLocation.value = weatherResponse
//    }

    /**
     * All the following code is derived from my Android book
     */
    // Collection of default data necessary for the View
    data class WeatherSummaryViewData(
        var latitude: Double = 0.0,
        var longitude: Double = 0.0,
        var temp: Double = 0.0,
        var feelsLikeTemp: Double = 0.0,
        var baroPressure: Int = 0,
        var humidity: Int = 0)

    // Helper method converting raw model data to view data
    fun weatherResponseToWeatherSummaryView(
        coordinates : Coord?,
        weather : Main?
    ) : WeatherSummaryViewData
    {
        if (coordinates != null && weather != null) {
            return WeatherSummaryViewData(
                coordinates.lat,
                coordinates.lon,
                weather.temp,
                weather.feels_like,
                weather.pressure,
                weather.humidity
            )
        }

        return WeatherSummaryViewData() // what to do if our coordinates or weather are null
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

            weatherSummaryViewData.value = weatherResponseToWeatherSummaryView(coordinates, baseWeatherStats)

//            if (!coordinates.isNullOrEmpty())
//            {
//                if (!baseWeatherStats.isNullOrEmpty())
//                {
//                    return baseWeatherStats.map { base ->
//                        weatherResponseToWeatherSummaryView(coordinates, base)
//                    }
//                }
//                return coordinates.map { latLng ->
//                    weatherResponseToWeatherSummaryView(latLng, baseWeatherStats)
//                }
//            }
        }
        return emptyList()
    }
}