package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.model.dao.CalculationsDao
import com.example.feelslike.model.dao.UserDao

class LandingPageViewModel(val database : UserDao, val calculations : CalculationsDao, application : Application
) : AndroidViewModel(application)
{
    val user = database.getAllUserInfo()

    private val _navigateToResultsFragment = MutableLiveData<Boolean?>()
    private val _navigateToProfileFragment = MutableLiveData<Boolean?>()

    val navigateToResultsFragment : LiveData<Boolean?>
        get() = _navigateToResultsFragment

    val navigateToProfileFragment : LiveData<Boolean?>
        get() = _navigateToProfileFragment

//    fun onCalculateClicked(plannedLocation)
//    {
//        _navigateToResultsFragment.value = plannedLocation
//    }

    fun onNavigated()
    {
        _navigateToResultsFragment.value = null
        _navigateToProfileFragment.value = null
    }

}