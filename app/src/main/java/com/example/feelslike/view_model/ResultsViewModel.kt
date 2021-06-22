package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.BuildConfig
import com.example.feelslike.model.weather_service.response.Coord
import com.example.feelslike.model.weather_service.response.Main
import com.example.feelslike.model.weather_service.response.WeatherResponse
import com.example.feelslike.model.weather_service.WeatherRepo

class ResultsViewModel(application : Application) : AndroidViewModel(application)
{
    private val TAG = ResultsViewModel::class.java.simpleName
    private var weatherRepo : WeatherRepo? = null

    lateinit var locationSummaryViewData: MutableLiveData<LocationSummaryViewData>
    lateinit var weatherSummaryViewData: MutableLiveData<WeatherSummaryViewData>

    private val _navigateToLandingPage = MutableLiveData<Boolean?>()
    private val _selectedLocation = MutableLiveData<WeatherResponse>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage
    val selectedLocation : LiveData<WeatherResponse>
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

    data class LocationSummaryViewData(
        var latitude: Double = 0.0,
        var longitude: Double = 0.0,
    )
    data class WeatherSummaryViewData(
        var temp: Double = 0.0,
        var feelsLikeTemp: Double = 0.0,
        var baroPressure: Int = 0,
        var humidity: Int = 0)

    // Helper method converting raw model data to view data
    private fun locationResponsetoWeatherSummaryView(
        coordinates : Coord?
    ) : LocationSummaryViewData
    {
        if (coordinates != null) {
            return LocationSummaryViewData(
                coordinates.lat,
                coordinates.lon,
            )
        }
        return LocationSummaryViewData() // what to do if our coordinates or weather are null
    }

    private fun weatherResponseToWeatherSummaryView(
    weather : Main?) : WeatherSummaryViewData {
        if (weather != null) {
            return WeatherSummaryViewData(
                weather.temp,
                weather.feels_like,
                weather.pressure,
                weather.humidity
            )
        }
        return WeatherSummaryViewData()
    }

    // This gets called by the Activity performing the search
//    suspend fun searchTemperature(location : String) : List<WeatherSummaryViewData>
//    {
//        val results = weatherRepo?.searchByPlace(location, BuildConfig.WEATHER_API_KEY)
//
//        if (results != null && results.isSuccessful)
//        {
//            val coordinates = results.body()?.coord
//            val baseWeatherStats = results.body()?.main
//
//            // TODO : Ask Brad for help - How to .map these data class responses
//            //  and check for not null or empty
//
//            locationSummaryViewData.value = locationResponsetoWeatherSummaryView(coordinates)
//            weatherSummaryViewData.value = weatherResponseToWeatherSummaryView(baseWeatherStats)
//
//            if (coordinates != null)
//            {
//                return coordinates.map { latLng ->
//                    locationResponsetoWeatherSummaryView(latLng)
//                }
//            }
//
//            if (baseWeatherStats != null)
//            {
//                return baseWeatherStats.map { base ->
//                    weatherResponseToWeatherSummaryView(base)
//                }
//            }
//        }
//        return emptyList()
//    }
}