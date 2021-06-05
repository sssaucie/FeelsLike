package com.example.feelslike.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.feelslike.model.dao.CalculationsDao
import com.example.feelslike.model.entity.CalculationsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class RecyclerViewFavoritesViewModel(val database : CalculationsDao,
                                     application : Application
) : AndroidViewModel(application)
{
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be
     * cancelled by calling 'viewModelJob.cancel()'.
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which
     * is the main thread on Android. This is a sensible default because most coroutines
     * started by a [ViewModel] update the UI after performing some processing.
     *
     * private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
     */

    val place = database.loadAllCalculationsInfo()

    private val _navigateToMaps = MutableLiveData<CalculationsEntity?>()
    val navigateToMaps
        get() = _navigateToMaps

    private val _navigateToLandingPage = MutableLiveData<Boolean?>()
    val navigateToLandingPage
        get() = _navigateToLandingPage

    fun onFavoritePlaceClicked(place : CalculationsEntity)
    {
        _navigateToMaps.value = place
    }

    fun onHomeButtonClicked()
    {
        _navigateToLandingPage.value = true
    }

    fun onNavigated()
    {
        _navigateToMaps.value = null
        _navigateToLandingPage.value = null
    }

    private suspend fun update(latitude: CalculationsEntity, longitude: CalculationsEntity)
    {
        withContext(Dispatchers.IO)
        {
            latitude.latitude
            longitude.longitude
        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines; otherwise, we end up with
     * processes that have nowhere to return to - using memory and resources.
     */
    override fun onCleared()
    {
        super.onCleared()
        viewModelJob.cancel()
    }
}