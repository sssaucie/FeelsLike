package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.UserEntity

class ResultsViewModel(application : Application) : AndroidViewModel(application)
{

}

//import androidx.lifecycle.MutableLiveData
//
//class ResultsViewModel
//{
//    // The internal MutableLiveData String that stores the status of the most recent request
//    private val _status = MutableLiveData<OpenWeatherApiStatus>()
//
//    // The navigation to get to the Weather Details screen
//    private val _navigateToWeatherDetails = MutableLiveData<WeatherDetails>()
//
//    private fun getOpenWeatherProperties()
//    {
//
//    }
//}