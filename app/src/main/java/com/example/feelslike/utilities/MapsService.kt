package com.example.feelslike.utilities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.feelslike.MainActivity
import com.example.feelslike.R
import com.example.feelslike.view_model.LandingPageViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

class MapsService
{
    companion object
    {
        const val EXTRA_FAVORITE_ID = "com.example.feelslike.EXTRA_FAVORITE_ID"
        const val REQUEST_LOCATION = 1
        private var TAG = MapsService::class.java.simpleName
        var mapFragment: SupportMapFragment? = null
        var mapReady = false

        /**
         * Singleton prevents multiple instances of maps opening at the same time.
         */

        @Volatile
        private var instance : MapsService? = null

        fun getInstance() : MapsService
        {
            /**
             * If the instance is not null, then return it.
             * If it is, then create the database.
             */
            return instance ?: synchronized(this)
            {
                instance ?: MapsService().also { instance = it }
            }
        }
    }

    private lateinit var map : GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    /**
     * Every fragment with a map included needs to call this function
     */
    @DelicateCoroutinesApi
    fun onMapReady(context: Context?, placesClient: PlacesClient, googleMap: GoogleMap) {
        if(context != null) {
            map = googleMap
            setupMapListeners(placesClient)
            createFavoritesMarkerObserver()
            getCurrentLocation(context)
            Log.i(TAG, "$map ready.")
        }
    }

    /**
     * Example [setupMap()] for individual fragments
     */
    private fun setupMap()
    {
//        (childFragmentManager.findFragmentById(R.id.map_layout) as
//                SupportMapFragment?)!!.getMapAsync(this)
        Log.i(TAG, "childFragmentManager in setupMap")
    }


    /**
     * Location logic
     */

    fun setupLocationClient(context: Context?)
    {
        if(context == null) {
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        Log.i(TAG, "Location Client setup")
    }

    private fun getCurrentLocation(context: Context?)
    {
        if(context == null) {
            return
        }
        if (context.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED)
        {
            if(context !is MainActivity) {
                throw RuntimeException("must be an instance of MainActivity")
            }
            (context as MainActivity).requestLocationPermission()
        }
        else
        {
            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnCompleteListener {
                val location = it.result
                if (location != null)
                {
                    val latLng = LatLng(location.latitude, location.longitude)
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                    map.moveCamera(update)
                    Log.i(TAG, "Map location set")
                }
                else
                {
                    Log.e(TAG, "No location found")
                }
            }
        }
    }

    fun setupPlacesClient(context: Context?): PlacesClient
    {
        if(context == null) {
            throw RuntimeException("Context was null")
        }
        try
        {
            context.let {
                var apiKey = context.getString(R.string.google_maps_key)
                Places.initialize(it, apiKey)
            }
            val placesClient = Places.createClient(context)
            Log.i(TAG, "Places Client set up")
            return placesClient
        }
        catch (e: Exception)
        {
            Log.i(TAG, "setupPlacesClient error: $e")
            throw RuntimeException("Error", e)
        }
    }

    private fun displayPoiNoContext(placesClient: PlacesClient, pointOfInterest : PointOfInterest)
    {
//        throw UnsupportedOperationException("need to change to places api")
//         displayPoiGetPlaceStep(placesClient, pointOfInterest)
    }

    fun displayPoi(context : Context?, placesClient: PlacesClient, place : Place)
    {
        if (context != null) {
            displayPoiGetPlaceStep(context, placesClient, place)
        }
    }

    private fun displayPoiGetPlaceStep(context : Context, placesClient: PlacesClient, place : Place)
    {
        val placeId = place.id
        val placeFields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.PHONE_NUMBER,
            Place.Field.PHOTO_METADATAS,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG,
            Place.Field.VIEWPORT)
        Log.i("map_testing","displayPoiGetPlaceStep Place Fields: $placeFields")
        val request = placeId?.let {
            FetchPlaceRequest
                .builder(it, placeFields)
                .build()
        }

        if (request != null) {
            placesClient.fetchPlace(request)
                .addOnSuccessListener { response ->
                    val place = response.place
                    displayPoiGetPhotoStep(context, placesClient, place)
                }.addOnFailureListener { exception ->
                    if (exception is ApiException)
                    {
                        val statusCode = exception.statusCode
                        Log.e(
                            TAG, "Place not found: ${exception.message}" +
                                    ", statusCode: $statusCode")
                    }
                }
        }
    }

    private fun displayPoiGetPhotoStep(context : Context, placesClient: PlacesClient, place : Place)
    {
        val photoMetaData = place
            .photoMetadatas?.get(0)

        if (photoMetaData == null)
        {
            displayPoiDisplayStep(place, null)
            return
        }

        val photoRequest = FetchPhotoRequest
            .builder(photoMetaData)
            .setMaxWidth(context.resources.getDimensionPixelSize(R.dimen.default_image_width))
            .setMaxHeight(context.resources.getDimensionPixelSize(R.dimen.default_image_height))
            .build()

        placesClient.fetchPhoto(photoRequest)
            .addOnSuccessListener { fetchPhotoResponse ->
                val bitmap = fetchPhotoResponse.bitmap
                displayPoiDisplayStep(place, bitmap)
            }.addOnFailureListener { exception ->
                if (exception is ApiException)
                {
                    val statusCode = exception.statusCode
                    Log.e(
                        TAG, "Place not found: ${exception.message}" +
                                ", statusCode: $statusCode")
                }
            }
    }

    private fun displayPoiDisplayStep(place : Place, photo : Bitmap?)
    {
        val marker = map.addMarker(MarkerOptions()
            .position(place.latLng as LatLng)
            .title(place.name)
            .snippet(place.address)
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(place.latLng!!))
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(place.viewport!!, 0))
        marker?.tag = PlaceInfo(place, photo)
        marker?.showInfoWindow()
    }

    @DelicateCoroutinesApi
    private fun handleInfoWindowClick(marker : Marker)
    {

        val placeInfo = (marker.tag as PlaceInfo)
//        if (placeInfo.place != null)
//        {
//            GlobalScope.launch {
//                placeInfo.image?.let {
//                    favoritesViewModel.addFavoritesBookmarkFromResults(
//                        placeInfo.place, it
//                    )
//                }
//            }
//            Log.i(TAG, "InfoWindow place clicked: ${placeInfo.place}")
//        }
        marker.remove()
        Log.i(TAG, "InfoWindow marker removed")
    }

    @DelicateCoroutinesApi
    private fun setupMapListeners(placesClient: PlacesClient)
    {
//        map.setInfoWindowAdapter(MapsInfoWidgetAdapter(activity))
        map.setOnPoiClickListener {
            displayPoiNoContext(placesClient, it)
        }
        map.setOnInfoWindowClickListener {
            handleInfoWindowClick(it)
        }
        Log.i(TAG, "Map Listeners set up")
    }

    private fun addFavoritePlaceMarker(
        favorite : LandingPageViewModel.FavoritesMarkerView) : Marker?
    {
        val marker = map.addMarker(MarkerOptions()
            .position(favorite.location)
            .title(favorite.name)
            .snippet(favorite.address)
            .icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_AZURE))
            .alpha(0.8f))

        marker?.tag = favorite

        Log.i(TAG, "Favorite marker added")

        return marker
    }

    private fun displayAllFavorites(
        favorites : List<LandingPageViewModel.FavoritesMarkerView>)
    {
        favorites.forEach { addFavoritePlaceMarker(it) }
    }

    private fun createFavoritesMarkerObserver()
    {
//        favoritesViewModel.getFavoritesMarkerViews()?.observe(
//            activity, {
//                map.clear()
//                it?.let {
//                    displayAllFavorites(it)
//                }
//            })
    }

    private fun startFavoriteDetails(favoriteId : Long)
    {
//        val intent = Intent(activity, Favorite)
    }

    private fun setMapLongClick(map: GoogleMap)
    {
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(R.string.calculate_here.toString())
                    .snippet(snippet)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_MAGENTA))
            )
        }
    }

    class PlaceInfo(val place : Place? = null,
        val image : Bitmap? = null)
}