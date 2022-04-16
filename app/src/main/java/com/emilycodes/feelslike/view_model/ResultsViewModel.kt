package com.emilycodes.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emilycodes.feelslike.BuildConfig
import com.emilycodes.feelslike.model.entity.weather.WeatherResponse
import com.emilycodes.feelslike.model.weather_service.WeatherApi
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResultsViewModel(selectedPlace : Place, application: Application) : AndroidViewModel(application)
{
    private val TAG = ResultsViewModel::class.java.simpleName

    private val unitOfMeasurement : String = "imperial"

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
            val getWeatherDeferred = WeatherApi.retrofitService.searchWeatherByPlaceName(location, unitOfMeasurement, BuildConfig.WEATHER_API_KEY)
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