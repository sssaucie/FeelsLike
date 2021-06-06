package com.example.feelslike.view_model

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.FavoritesEntity
import com.example.feelslike.utilities.FeelsLikeRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.Job

class RecyclerViewFavoritesViewModel(
    application : Application) : AndroidViewModel(application)
{
    private val TAG = "RecyclerViewFavoritesViewModel"
    private val favoriteRepo : FeelsLikeRepository = FeelsLikeRepository(getApplication())
    private var favorites : LiveData<List<FavoriteMarkerView>>? = null
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

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

    fun addFavoriteFromPlace(place : Place, image : Bitmap?)
    {
        val favorite = favoriteRepo.createFavorite()
        favorite.favorite_id = place.id
        favorite.place_name = place.name.toString()
        favorite.place_lat = place.latLng?.latitude ?: 0.0
        favorite.place_lon = place.latLng?.longitude ?: 0.0

        val newId = favoriteRepo.addFavorite(favorite)

        image?.let { favorite.setImage(it, getApplication()) }

        Log.i(TAG, "New favorite $newId added to the database.")
    }

    private fun favoritesToMarkerView(favorite : FavoritesEntity) = FavoriteMarkerView(
        favorite.favorites_entity_id, LatLng(favorite.place_lat, favorite.place_lon))

    private fun mapFavoritesToMarkerView()
    {
        favorites = Transformations.map(favoriteRepo.allFavorites)
        {
            repoFavorites -> repoFavorites.map { favorite ->
            favoritesToMarkerView(favorite) }
        }
    }

    fun getFavoriteMarkerViews() : LiveData<List<FavoriteMarkerView>>?
    {
        if (favorites == null)
        {
            mapFavoritesToMarkerView()
        }
        return favorites
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

    data class FavoriteMarkerView(
        var id : Long? = null,
        var location : LatLng = LatLng(0.0, 0.0))
}