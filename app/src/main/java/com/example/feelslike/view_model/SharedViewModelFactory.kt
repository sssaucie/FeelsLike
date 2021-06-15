package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feelslike.utilities.FeelsLikeRepository

class SharedViewModelFactory (
    private val application: Application,
    private val intentRepo : FeelsLikeRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(application, intentRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}