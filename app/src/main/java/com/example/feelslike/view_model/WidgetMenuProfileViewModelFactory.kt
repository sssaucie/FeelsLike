package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.UserEntity

class WidgetMenuProfileViewModelFactory (
    private val application : Application
) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(WidgetMenuProfileViewModel::class.java))
        {
            return WidgetMenuProfileViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}