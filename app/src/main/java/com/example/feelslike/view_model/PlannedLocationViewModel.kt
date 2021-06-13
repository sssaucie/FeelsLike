package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.utilities.FeelsLikeRepository
import com.google.android.libraries.places.api.Places

class PlannedLocationViewModel(application : Application) : AndroidViewModel(application)
{
    private val TAG = PlannedLocationViewModel::class.java.simpleName
    private val dataRepository : FeelsLikeRepository = FeelsLikeRepository(getApplication())
    private val _navigateToLandingPage = MutableLiveData<Boolean?>()
    private val _navigateExitToLandingPage = MutableLiveData<Boolean?>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage

    val navigateExitToLandingPage : LiveData<Boolean?>
        get() = _navigateExitToLandingPage

    fun setLocationFromPlace(place : Places)
    {
        val location = dataRepository.createCalculationsInfo()
//        location.longitude = place.latLng?.longitude ?: 0.0
//        location.latitude = place.latLng?.latitude ?: 0.0
//
//        val newId = dataRepository.addCalculation(place)
//        Log.i(TAG, "Location $newId set.")
    }

    fun onSetLocationButtonClicked()
    {
        _navigateToLandingPage.value = true
        Log.i(TAG, "Set Location button clicked.")
    }

    fun onExitButtonClicked()
    {
        _navigateExitToLandingPage.value = true
        Log.i(TAG, "Exit button clicked.")
    }

    fun doneNavigating()
    {
        _navigateToLandingPage.value = null
        _navigateExitToLandingPage.value = null
        Log.i(TAG, "Navigation cleared.")
    }
}