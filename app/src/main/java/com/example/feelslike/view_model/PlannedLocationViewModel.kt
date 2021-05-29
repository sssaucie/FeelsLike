package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.model.dao.CalculationsDao

class PlannedLocationViewModel(
    val database : CalculationsDao, application : Application) : AndroidViewModel(application)
{
    private val _navigateToLandingPage = MutableLiveData<Boolean?>()
    private val _navigateExitToLandingPage = MutableLiveData<Boolean?>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage

    val navigateExitToLandingPage : LiveData<Boolean?>
        get() = _navigateExitToLandingPage

    fun onSetLocationButtonClicked()
    {
        _navigateToLandingPage.value = true
    }

    fun onExitButtonClicked()
    {
        _navigateExitToLandingPage.value = true
    }

    fun doneNavigating()
    {
        _navigateToLandingPage.value = null
        _navigateExitToLandingPage.value = null
    }
}