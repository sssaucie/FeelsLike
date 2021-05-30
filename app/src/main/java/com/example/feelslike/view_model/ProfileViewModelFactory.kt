package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feelslike.model.entity.UserEntity

class ProfileViewModelFactory(
    private val user : UserEntity,
    private val application : Application
) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T
    {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
        {
            return ProfileViewModel(user, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}