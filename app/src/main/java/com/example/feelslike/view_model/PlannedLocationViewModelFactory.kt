package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlannedLocationViewModelFactory(private val application : Application
) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T
    {
        if (modelClass.isAssignableFrom(PlannedLocationViewModel::class.java))
        {
            return PlannedLocationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}