package com.example.feelslike.view_model

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import com.example.feelslike.model.dao.UserDao
import kotlinx.coroutines.Job

class InitialUserInputViewModel(
    val database : UserDao, application : Application) : AndroidViewModel(application)
{
    private var viewModelJob = Job()

    var firstName = MutableLiveData<String>()
    var lastName = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var heightFeet = MutableLiveData<Int>()
    var heightInches = MutableLiveData<Int>()
    var weightPounds = MutableLiveData<Float>()

    private val _navigateToLandingPage = MutableLiveData<Boolean?>()

    val navigateToLandingPage : LiveData<Boolean?>
        get() = _navigateToLandingPage

    fun onContinueButtonClicked()
    {
        _navigateToLandingPage.value = true
    }

    fun doneNavigating()
    {
        _navigateToLandingPage.value = null
    }
}