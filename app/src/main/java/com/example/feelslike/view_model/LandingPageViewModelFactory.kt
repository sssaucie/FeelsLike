package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feelslike.model.dao.CalculationsDao
import com.example.feelslike.model.dao.UserDao
import com.example.feelslike.model.entity.UserEntity

class LandingPageViewModelFactory (
    private val dataSource: UserDao,
    private val calculationsSource: CalculationsDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LandingPageViewModel::class.java)) {
            return LandingPageViewModel(dataSource, calculationsSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}