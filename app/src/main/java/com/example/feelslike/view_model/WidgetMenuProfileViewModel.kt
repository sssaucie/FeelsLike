package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.UserEntity

class WidgetMenuProfileViewModel (val user: UserEntity, calculations : CalculationsEntity, application : Application) : AndroidViewModel(application)
{
    private val _userInfo = MutableLiveData<UserEntity>()

    val userInfo : LiveData<UserEntity>
        get() = _userInfo

    init {
        _userInfo.value = user
    }

    private val _navigateToProfileFragment = MutableLiveData<Boolean?>()

    // TODO: This is a nice-to-have feature we can implement after MVP
    private val _navigateToMenu = MutableLiveData<Boolean?>()

    val navigateToProfileFragment : LiveData<Boolean?>
        get() = _navigateToProfileFragment

    fun onProfilePictureClicked(user : UserEntity)
    {
        _navigateToProfileFragment.value = user
    }

    fun onNavigated()
    {
        _navigateToProfileFragment.value = null
    }

}