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
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.example.feelslike.MainActivity
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentMapsBinding
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.utilities.FeelsLikeRepository
import com.example.feelslike.utilities.KEY_LOCATION
import com.example.feelslike.utilities.MapsInfoWidgetAdapter
import com.example.feelslike.view_model.SharedViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MapsFragment : SupportMapFragment(), OnMapReadyCallback
{
    lateinit var map: GoogleMap
    private var mapReady = false
    private lateinit var binding : FragmentMapsBinding
    private lateinit var activity : MainActivity
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var placesClient : PlacesClient
    private lateinit var viewModel : SharedViewModel
    private lateinit var selectedPlace : CalculationsEntity
    private lateinit var dataRepo : FeelsLikeRepository

    // A default location (Sydney, Australia) to use when location permission is not granted
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    // TODO: Create variable for planned location, change onCreateView to analyze
    // whether a planned location has been passed, if else lastKnownLocation, else default.

    private var lastKnownLocation : Location? = null

    private val favoritesViewModel by viewModels<SharedViewModel>()

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ): View?
    {
//        selectedPlace = if (mapReady && selectedPlace != null)
//        {
//            MapsFragmentArgs.fromBundle(requireArguments()).selectedPlace
//        }
//        else
//        {
//            setDefaultPlace()
//        }
        
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        if (savedInstanceState != null)
        {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
        }
        else
        {
            defaultLocation
        }

        binding = FragmentMapsBinding.inflate(layoutInflater)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_layout) as? SupportMapFragment
        mapFragment?.getMapAsync {
            googleMap -> map = googleMap
            mapReady = true
            updateMap()
        }

        setupLocationClient()
        setupMap()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        requireActivity().let {
            viewModel = ViewModelProviders.of(it)
                .get(SharedViewModel::class.java)
        }

        viewModel.navigateToResultsFragment.observe(viewLifecycleOwner, { selectedPlace ->
            selectedPlace?.let {
//                this.findNavController().navigate()
            }
            updateMap()
        })
    }

    private fun updateMap() {
        if (mapReady && selectedPlace != null)
        {
            dataRepo.createCalculationsInfo().longitude = selectedPlace.longitude
            dataRepo.createCalculationsInfo().latitude = selectedPlace.latitude
            dataRepo.createCalculationsInfo().calculations_id = selectedPlace.calculations_id

            val marker = LatLng(selectedPlace.latitude, selectedPlace.longitude)
            map.addMarker(MarkerOptions()
                .position(marker))
        }
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupPlacesClient()
        Log.i(TAG, "Map view created.")
    }

    @DelicateCoroutinesApi
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setupMapListeners()
        createFavoritesMarkerObserver()
        getCurrentLocation()
        Log.i(TAG, "$map ready.")
    }

    private fun setupMap()
    {
        (childFragmentManager.findFragmentById(R.id.map_layout) as
                SupportMapFragment?)!!.getMapAsync(this)
        Log.i(TAG, "childFragmentManager in setupMap")
    }

    private fun requestLocationPermission()
    {
        ActivityCompat.requestPermissions(activity, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        Log.i(TAG, "Permissions requested.")
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
                Log.i(TAG, "Current location accessed.")
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
        Log.i(TAG, "Location Client setup")
    }

    private fun getCurrentLocation()
    {
        if (requireContext().let {
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
                    Log.i(TAG, "Map location set")
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
        Log.i(TAG, "Places Client set up")
    }

    companion object
    {
        const val EXTRA_FAVORITE_ID = "com.example.feelslike.EXTRA_FAVORITE_ID"
        private const val REQUEST_LOCATION = 1
        private var TAG = MapsFragment::class.java.simpleName
        var mapFragment : SupportMapFragment?=null
        fun newInstance() = MapFragment()
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
                displayPoiGetPhotoStep(place)
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
                    Log.e(TAG, "Place not found: ${exception.message}" +
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
        marker?.tag = PlaceInfo(place, photo)
        marker?.showInfoWindow()
    }

    @DelicateCoroutinesApi
    private fun handleInfoWindowClick(marker : Marker)
    {
        val placeInfo = (marker.tag as PlaceInfo)
        if (placeInfo.place != null)
        {
            GlobalScope.launch {
                placeInfo.image?.let {
                    favoritesViewModel.addFavoritesBookmarkFromResults(
                        placeInfo.place, it
                    )
                }
            }
            Log.i(TAG, "InfoWindow place clicked: ${placeInfo.place}")
        }
        marker.remove()
        Log.i(TAG, "InfoWindow marker removed")
    }

    @DelicateCoroutinesApi
    private fun setupMapListeners()
    {
        map.setInfoWindowAdapter(MapsInfoWidgetAdapter(activity))
        map.setOnPoiClickListener {
            displayPoi(it)
        }
        map.setOnInfoWindowClickListener {
            handleInfoWindowClick(it)
        }
        Log.i(TAG, "Map Listeners set up")
    }

    private fun addFavoritePlaceMarker(
        favorite : SharedViewModel.FavoritesMarkerView) : Marker?
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
        favorites : List<SharedViewModel.FavoritesMarkerView>)
    {
        favorites.forEach { addFavoritePlaceMarker(it) }
    }

    private fun createFavoritesMarkerObserver()
    {
        favoritesViewModel.getFavoritesMarkerViews()?.observe(
            activity, {
                map.clear()
                it?.let {
                    displayAllFavorites(it)
                }
            })
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
                    .title(getString(R.string.calculate_here))
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