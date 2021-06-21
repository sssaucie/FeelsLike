package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feelslike.model.weather_service.WeatherResponse

/**
 * Simple ViewModel factory that provides the MarsProperty and context to the ViewModel.
 */
class ResultsViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
            return ResultsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}