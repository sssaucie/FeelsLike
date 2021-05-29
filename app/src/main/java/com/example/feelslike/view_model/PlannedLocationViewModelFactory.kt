package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feelslike.model.dao.CalculationsDao

class PlannedLocationViewModelFactory(
    private val dataSource : CalculationsDao,
    private val application : Application
) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T
    {
        if (modelClass.isAssignableFrom(PlannedLocationViewModel::class.java))
        {
            return PlannedLocationViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}