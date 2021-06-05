package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.model.dao.CalculationsDao
import com.example.feelslike.model.dao.UserDao
import com.example.feelslike.utilities.FeelsLikeRepository

class LandingPageViewModel(application : Application) : AndroidViewModel(application)
{
    private val TAG = "LandingPageViewModel"
    private val dataRepository : FeelsLikeRepository = FeelsLikeRepository(getApplication())

    private val _navigateToResultsFragment = MutableLiveData<Boolean?>()
    private val _navigateToProfileFragment = MutableLiveData<Boolean?>()
    private val _navigateToPlannedLocationFragment = MutableLiveData<Boolean?>()

    val navigateToResultsFragment : LiveData<Boolean?>
        get() = _navigateToResultsFragment

    val navigateToProfileFragment : LiveData<Boolean?>
        get() = _navigateToProfileFragment

    val navigateToPlannedLocationFragment : LiveData<Boolean?>
        get() = _navigateToPlannedLocationFragment

//    fun onCalculateClicked(plannedLocation)
//    {
//        _navigateToResultsFragment.value = plannedLocation
//        Log.i(TAG, "Calculate button clicked.")
//    }

    fun onProfilePhotoClicked()
    {
        _navigateToProfileFragment.value = true
        Log.i(TAG, "Profile photo clicked.")
    }

    fun onPlannedLocationClicked()
    {
        _navigateToPlannedLocationFragment.value = true
        Log.i(TAG, "Planned Location button clicked.")
    }

    fun onCurrentLocationClicked()
    {
        Log.i(TAG, "Current Location button clicked.")
    }

    fun onNavigated()
    {
        _navigateToResultsFragment.value = null
        _navigateToProfileFragment.value = null
        _navigateToPlannedLocationFragment.value = null
        Log.i(TAG, "All navigation cleared.")
    }

}