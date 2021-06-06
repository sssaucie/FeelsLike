package com.example.feelslike.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.feelslike.MainActivity
import com.example.feelslike.R
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.utilities.KEY_LOCATION
import com.example.feelslike.utilities.MapsInfoWidgetAdapter
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import java.util.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

class MapsFragment : SupportMapFragment(), OnMapReadyCallback
{
    private lateinit var preferredLocation : CalculationsEntity
    private lateinit var map: GoogleMap
    private lateinit var activity : MainActivity
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var placesClient : PlacesClient

    // A default location (Sydney, Australia) to use when location permission is not granted
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    // TODO: Create variable for planned location, change onCreateView to analyze
    // whether a planned location has been passed, if else lastKnownLocation, else default.

    private var lastKnownLocation : Location? = null

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        onMapReady(googleMap)
    }

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ): View?
    {
        if (savedInstanceState != null)
        {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
        }
        else
        {
            defaultLocation
        }
        preferredLocation = MapsFragmentArgs.fromBundle(requireArguments()).currentSelection
        setupLocationClient()
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        setupPlacesClient()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setInfoWindowAdapter(MapsInfoWidgetAdapter(activity))
        getCurrentLocation()
        map.setOnPoiClickListener {
            displayPoi(it)
        }
    }

    private fun requestLocationPermission()
    {
        ActivityCompat.requestPermissions(activity, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode : Int,
        permissions : Array<out String>,
        grantResults : IntArray)
    {
        if (requestCode == REQUEST_LOCATION)
        {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation()
            }
            else
            {
                Log.e(TAG, "Location permission denied")
            }
        }
    }

    private fun setupLocationClient()
    {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    private fun getCurrentLocation()
    {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED)
        {
            requestLocationPermission()
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
                }
                else
                {
                    Log.e(TAG, "No location found")
                }
            }
        }
    }

    private fun setupPlacesClient()
    {
        context?.let { Places.initialize(it, getString(R.string.google_maps_key)) }
        placesClient = Places.createClient(activity)
    }

    companion object
    {
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MainActivity"
    }

    private fun displayPoi(pointOfInterest : PointOfInterest)
    {
        displayPoiGetPlaceStep(pointOfInterest)
    }

    private fun displayPoiGetPlaceStep(pointOfInterest : PointOfInterest)
    {
        val placeId = pointOfInterest.placeId
        val placeFields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.PHONE_NUMBER,
            Place.Field.PHOTO_METADATAS,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG)
        val request = FetchPlaceRequest
            .builder(placeId, placeFields)
            .build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                Toast.makeText(
                    activity,
                    "${place.name}, " + "${place.phoneNumber}",
                    Toast.LENGTH_LONG).show()
            }.addOnFailureListener { exception ->
                if (exception is ApiException)
                {
                    val statusCode = exception.statusCode
                    Log.e(
                        TAG,
                        "Place not found: ${exception.message}" +
                                ", statusCode: $statusCode")
                }
            }
    }

    private fun displayPoiGetPhotoStep(place : Place)
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
            .setMaxWidth(resources.getDimensionPixelSize(
                R.dimen.default_image_width))
            .setMaxHeight(resources.getDimensionPixelSize(
                R.dimen.default_image_height))
            .build()

        placesClient.fetchPhoto(photoRequest)
            .addOnSuccessListener { fetchPhotoResponse ->
                val bitmap = fetchPhotoResponse.bitmap
                displayPoiDisplayStep(place, bitmap)
            }.addOnFailureListener { exception ->
                if (exception is ApiException)
                {
                    val statusCode = exception.statusCode
                    Log.e(TAG,
                        "Place not found: ${exception.message}" +
                                ", statusCode: $statusCode")
                }
            }
    }

    private fun displayPoiDisplayStep(place : Place, photo : Bitmap?)
    {
        val marker = map.addMarker(MarkerOptions()
            .position(place.latLng as LatLng)
            .title(place.name)
            .snippet(place.phoneNumber))
        marker?.tag = photo
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
                    .title(getString(R.string.calculate_here))
                    .snippet(snippet)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_MAGENTA))
            )
        }
    }
}