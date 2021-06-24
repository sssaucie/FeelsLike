package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class InitialUserInputViewModel(application : Application) : AndroidViewModel(application){}
//{
//    private val TAG = "InitialUserInputViewModel"
//    private val feelsLikeRepository : FeelsLikeRepository = FeelsLikeRepository(getApplication())
//
//    private val _navigateToLandingPage = MutableLiveData<Boolean?>()
//    private val _navigateSkipToLandingPage = MutableLiveData<Boolean?>()
//
//    val navigateToLandingPage : LiveData<Boolean?>
//        get() = _navigateToLandingPage
//
//    val navigateSkipToLandingPage : LiveData<Boolean?>
//        get() = _navigateSkipToLandingPage
//
//    fun addUser(addUser : UserEntity)
//    {
//        val user = feelsLikeRepository.createUser()
//        user.user_entity_id = addUser.user_id
//        user.first_name = addUser.first_name.toString()
//        user.last_name = addUser.last_name.toString()
//        user.email_address = addUser.email_address.toString()
//        user.height = addUser.height
//        user.weight = addUser.weight
//        // This next one is nice to have, but not currently feasible.
////        user.preferred_temp = addUser.preferred_temp
//        val newId = feelsLikeRepository.addUser(user)
//
//        Log.i(TAG, "New user $newId added to the database.")
//    }
//
//    fun onContinueButtonClicked()
//    {
//        _navigateToLandingPage.value = true
//        Log.i(TAG, "Continue button clicked.")
//    }
//
//    fun onSkipButtonClicked()
//    {
//        _navigateSkipToLandingPage.value = true
//        Log.i(TAG, "Skip button clicked.")
//    }
//
//    fun doneNavigating()
//    {
//        _navigateToLandingPage.value = null
//        _navigateSkipToLandingPage.value = null
//        Log.i(TAG, "Navigation cleared.")
//    }
//
//    fun createUser(
//        firstName: String,
//        lastName: String,
//        email: String,
//        isMetric: Boolean,
//        heightFeet: Int,
//        heightInches: Int,
//        weightPounds: Float)
//    {
//        val metricValue : Int = if (isMetric) 1 else 0
//
//        val preferredTemp = 72F
//        val height : Float = if (metricValue == 1) heightFeet.toFloat()
//                             else (heightFeet.toFloat() * 12) + heightInches
//        val user = UserEntity(
//            0,
//            0,
//            firstName,
//            lastName,
//            email,
//            metricValue,
//            preferredTemp,
//            height,
//            weightPounds)
//
//
//        viewModelScope.launch {
//            feelsLikeRepository.addUser(user)
//            Log.i(TAG, "createUser function called and launched. " +
//                    "User $user added to the database.")
//        }
//    }
//}