package com.example.feelslike.view_model

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.feelslike.BuildConfig
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.FavoritesEntity
import com.example.feelslike.model.weather_service.WeatherApi
import com.example.feelslike.utilities.FeelsLikeRepository
import com.example.feelslike.utilities.ImageUtil
import com.example.feelslike.model.weather_service.WeatherRepo
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LandingPageViewModel(application : Application) : AndroidViewModel(application)
{
    private val TAG = LandingPageViewModel::class.java.simpleName
    private val dataRepository: FeelsLikeRepository = FeelsLikeRepository(getApplication())
    private var bookmarks: LiveData<List<FavoritesMarkerView>>? = null
    private var weatherRepo : WeatherRepo? = null

    private val _navigateToResultsFragment = MutableLiveData<Place?>()
    private val _navigateToProfileFragment = MutableLiveData<Boolean?>()
    private val _navigateToPlannedLocationFragment = MutableLiveData<Boolean?>()
    private val _navigateToInitialUserInputFragment = MutableLiveData<Boolean?>()
    private val _navigateToMapFragment = MutableLiveData<Boolean?>()
    private val _navigateToRecyclerViewFavorites = MutableLiveData<Boolean?>()


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob+ Dispatchers.Main)

    val navigateToResultsFragment: LiveData<Place?>
        get() = _navigateToResultsFragment

    val navigateToProfileFragment: LiveData<Boolean?>
        get() = _navigateToProfileFragment

    val navigateToPlannedLocationFragment: LiveData<Boolean?>
        get() = _navigateToPlannedLocationFragment

    val navigateToInitialUserInputFragment: LiveData<Boolean?>
        get() = _navigateToInitialUserInputFragment

    val navigateToMapFragment: LiveData<Boolean?>
        get() = _navigateToMapFragment

    val navigateToRecyclerViewFavorites: LiveData<Boolean?>
        get() = _navigateToRecyclerViewFavorites

    init {
        _navigateToResultsFragment.value = null
    }

//    fun addPlaceFromCalculations(place: CalculationsEntity)
//    {
//        val bookmark = dataRepository.createCalculationsInfo()
//
//        bookmark.calculations_id = place.calculations_id
//        bookmark.latitude = place.latitude
//        bookmark.longitude = place.longitude
//
//        val newId = dataRepository.addCalculation(bookmark)
//
//        Log.i(TAG, "New calculation $newId added to the database.")
//    }

    fun addFavoritesBookmarkFromResults(place: Place, image: Bitmap)
    {
        val bookmark = dataRepository.createFavorite()

        bookmark.favorite_id = place.id
        bookmark.place_name = place.name.toString()
        bookmark.place_lat = place.latLng?.latitude ?: 0.0
        bookmark.place_lon = place.latLng?.longitude ?: 0.0

        val newId = dataRepository.addFavorite(bookmark)
        image?.let { bookmark.setImage(it, getApplication()) }

        Log.i(TAG, "New bookmark $newId added to the database.")
    }

    fun onCalculateClicked(lastSelectedLocation : Place)
    {
        Log.i(TAG, "Calculate button clicked.")
        _navigateToResultsFragment.value = lastSelectedLocation
    }

    fun onPlannedLocationClicked()
    {
        _navigateToPlannedLocationFragment.value = true
        Log.i(TAG, "Planned Location button clicked.")
    }

    fun onCurrentLocationClicked()
    {
        _navigateToMapFragment.value = true
        Log.i(TAG, "Current Location button clicked.")
    }

    fun onProfilePictureClicked()
    {
        _navigateToProfileFragment.value = true
        Log.i(TAG, "Profile picture clicked.")
    }

    fun onFavoriteHeartClicked()
    {
        _navigateToRecyclerViewFavorites.value = true
        Log.i(TAG, "Favorites heart clicked ON")
    }

    fun onNavigated()
    {
        _navigateToResultsFragment.value = null
        _navigateToProfileFragment.value = null
        _navigateToPlannedLocationFragment.value = null
        _navigateToInitialUserInputFragment.value = null
        _navigateToMapFragment.value = null
        _navigateToRecyclerViewFavorites.value = null
        _navigateToResultsFragment.value = null
        Log.i(TAG, "All navigation cleared.")
    }

    // Helper method that converts a [FavoritesEntity] object from
    // the repository into a [FavoritesMarkerView] object
    private fun favoritesToMarkerView(bookmark: FavoritesEntity) = FavoritesMarkerView(
        bookmark.favorites_entity_id, LatLng(bookmark.place_lat, bookmark.place_lon))

    /**
     * Use the [Transformations] class to dynamically map [FavoritesEntity] objects
     * into [FavoritesMarkerView] objects as they get updated in the database. This class is
     * provided by the Lifecycle package as a convenient way to transform values in a
     * LiveData object before they are returned to the observer.
     *
     * [Transformations.map] provides a list of [FavoritesEntity] returned from the
     * [FeelsLikeRepository]. The list is stored in the [bookmarks] variable.
     */
    private fun mapFavoritesToMarkerView()
    {
        bookmarks = Transformations.map(dataRepository.allFavorites)
        { repositoryBookmarks ->
            repositoryBookmarks.map { favorite ->
                favoritesToMarkerView(favorite)
            }
        }
    }

    // Initialize and return the bookmarks for favorites.
    fun getFavoritesMarkerViews(): LiveData<List<FavoritesMarkerView>>?
    {
        if (bookmarks == null)
        {
            mapFavoritesToMarkerView()
        }
        return bookmarks
    }

    data class FavoritesMarkerView(
        var id: Long? = null,
        var location: LatLng = LatLng(0.0, 0.0),
        var name: String = "",
        var address: String = ""
    ) {
        // Verify a valid ID, then load the image from the autogenerated filename
        // in FavoritesEntity.
        fun getImage(context: Context) = id?.let {
            ImageUtil.loadBitmapFromFile(context, FavoritesEntity.generateImageFilename(it))
        }
    }

    fun getWeatherResults(location:String)
    {
        coroutineScope.launch {
            var getWatherDeferred = WeatherApi.retrofitService.searchWeatherByPlaceName(location,
                BuildConfig.WEATHER_API_KEY)

        }

    }
}