package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.UserEntity
import com.example.feelslike.utilities.FeelsLikeRepository

class WidgetMenuProfileViewModel (application : Application) : AndroidViewModel(application)
{
    private val TAG = "WidgetMenuProfileViewModel"
    private val dataRepository : FeelsLikeRepository = FeelsLikeRepository(getApplication())
    private val _userInfo = MutableLiveData<UserEntity>()

//    val userInfo : LiveData<UserEntity>
//        get() = _userInfo
//
//    init {
//        _userInfo.value = user
//    }

    private val _navigateToProfileFragment = MutableLiveData<Boolean?>()

    // TODO: This is a nice-to-have feature we can implement after MVP
    private val _navigateToMenu = MutableLiveData<Boolean?>()

    val navigateToProfileFragment : LiveData<Boolean?>
        get() = _navigateToProfileFragment

    // TODO: Pass in user information
    fun onProfilePictureClicked(user : UserEntity)
    {
        _navigateToProfileFragment.value = true
        Log.i(TAG, "Profile picture clicked.")
    }

    fun onNavigated()
    {
        _navigateToProfileFragment.value = null
        Log.i(TAG, "Navigation cleared.")
    }

}