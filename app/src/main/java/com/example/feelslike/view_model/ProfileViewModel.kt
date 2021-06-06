package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.model.entity.UserEntity
import com.example.feelslike.utilities.FeelsLikeRepository

class ProfileViewModel(application: Application) : AndroidViewModel(application)
{
    private val TAG = "ProfileViewModel"
    private val dataRepository : FeelsLikeRepository = FeelsLikeRepository(getApplication())
    private val _user = MutableLiveData<UserEntity>()

//    val user : LiveData<UserEntity>
//        get() = _user
//
//    init {
//        _user.value = user
//    }

    private val _navigateToLandingPage = MutableLiveData<Boolean?>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage

    fun getUserFromDatabase(user : UserEntity)
    {
        Log.i(TAG, "User populated from database.")
    }

    fun onHomeButtonClicked()
    {
        _navigateToLandingPage.value = true
        Log.i(TAG, "Home button clicked.")
    }

    fun onNavigated()
    {
        _navigateToLandingPage.value = null
        Log.i(TAG, "Navigation cleared.")
    }
}