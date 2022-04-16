package com.emilycodes.feelslike.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.model.Place

/**
 * Simple ViewModel factory that provides the MarsProperty and context to the ViewModel.
 */
class ResultsViewModelFactory(
    private val lastSelectedLocation : Place,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
            return ResultsViewModel(lastSelectedLocation, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}