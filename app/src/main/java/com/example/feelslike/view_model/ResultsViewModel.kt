package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.BuildConfig
import com.example.feelslike.model.entity.weather.*
import com.example.feelslike.model.weather_service.WeatherApi
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.*
import java.lang.Exception

class ResultsViewModel(selectedPlace : Place, application: Application) : AndroidViewModel(application)
{
    private val TAG = ResultsViewModel::class.java.simpleName

    private val _navigateToLandingPage = MutableLiveData<Boolean?>()
    private val _selectedLocation = MutableLiveData<Place>()
    private val _weatherResults = MutableLiveData<WeatherResponse?>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage
    val selectedLocation : LiveData<Place>
        get() = _selectedLocation
    val weatherResults : LiveData<WeatherResponse?>
        get() = _weatherResults

    init {
        _selectedLocation.value = selectedPlace
    }
    //
    /// coroutine Scope Instance
    //
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    //
    //
    //

    fun returnWeatherResults()
    {

    }

    fun onSearchAgainClicked()
    {
        _navigateToLandingPage.value = true
        Log.i(TAG, "Search Again clicked")
    }

    fun onNavigated()
    {
        _navigateToLandingPage.value = null
    }

    fun getWeatherResults(location : String)
{
        coroutineScope.launch {
            val getWeatherDeferred = WeatherApi.retrofitService.searchWeatherByPlaceName(location, BuildConfig.WEATHER_API_KEY)
            try {
                val listResult = getWeatherDeferred
                _weatherResults.value = listResult
            } catch (e: Exception)
            {
                _weatherResults.value = null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}