package com.example.feelslike.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.utilities.FeelsLikeRepository

class ResultsViewModel(application : Application) : AndroidViewModel(application)
{
    private val TAG = "ResultsViewModel"
    private val feelsLikeRepository : FeelsLikeRepository = FeelsLikeRepository(getApplication())

    private val _navigateToRecyclerViewFavorites = MutableLiveData<Boolean?>()

    val navigateToRecyclerViewFavorites : LiveData<Boolean?>
        get() = _navigateToRecyclerViewFavorites

    fun onFavoriteHeartClicked()
    {
        _navigateToRecyclerViewFavorites.value = true
        Log.i(TAG, "Favorites heart clicked ON")
    }

    fun onNavigated()
    {
        _navigateToRecyclerViewFavorites.value = null
        Log.i(TAG, "All navigation cleared.")
    }
}