package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.feelslike.model.entity.UserEntity

class ProfileViewModel(user : UserEntity, application: Application) : AndroidViewModel(application)
{
    private val _user = MutableLiveData<UserEntity>()

    val user : LiveData<UserEntity>
        get() = _user

    init {
        _user.value = user
    }

    private val _navigateToLandingPage = MutableLiveData<Boolean?>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage

    fun onHomeButtonClicked()
    {
        _navigateToLandingPage.value = true
    }

    fun onNavigated()
    {
        _navigateToLandingPage.value = null
    }
}