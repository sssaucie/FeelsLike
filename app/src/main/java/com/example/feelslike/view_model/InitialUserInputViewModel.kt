package com.example.feelslike.view_model

import android.app.Application
import android.text.Editable
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.feelslike.model.dao.UserDao
import com.example.feelslike.model.entity.UserEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class InitialUserInputViewModel(
    val database : UserDao, application : Application) : AndroidViewModel(application)
{
    private val _navigateToLandingPage = MutableLiveData<Boolean?>()
    private val _navigateSkipToLandingPage = MutableLiveData<Boolean?>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage

    val navigateSkipToLandingPage : LiveData<Boolean?>
        get() = _navigateSkipToLandingPage

    fun onContinueButtonClicked()
    {
        _navigateToLandingPage.value = true
    }

    fun onSkipButtonClicked()
    {
        _navigateSkipToLandingPage.value = true
    }

    fun doneNavigating()
    {
        _navigateToLandingPage.value = null
        _navigateSkipToLandingPage.value = null
    }

    fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        isMetric: Boolean,
        heightFeet: Int,
        heightInches: Int,
        weightPounds: Float)
    {
        val metricValue : Int = if (isMetric) 1 else 0

        val preferredTemp = 72F
        val height : Float = if (metricValue == 1) heightFeet.toFloat()
                             else (heightFeet.toFloat() * 12) + heightInches
        val user = UserEntity(
            0,
            firstName,
            lastName,
            email,
            metricValue,
            preferredTemp,
            height,
            weightPounds)


        viewModelScope.launch {
            database.insert(user)
        }
    }
}