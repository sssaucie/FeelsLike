package com.example.feelslike.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.feelslike.MainActivity
import com.example.feelslike.R
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.utilities.KEY_LOCATION
import com.example.feelslike.utilities.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : SupportMapFragment(), OnMapReadyCallback
{
    private lateinit var currentSelection : CalculationsEntity
    private lateinit var map: GoogleMap
    private lateinit var activity : MainActivity

    // A default location (Sydney, Australia) to use when location permission is not granted
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null)
        {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
        }
        currentSelection = MapsFragmentArgs.fromBundle(requireArguments()).currentSelection
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        updateLocationUI()
        val zoomLevel = 7.5F
        map.addMarker(
            MarkerOptions()
                .position(LatLng(currentSelection.latitude, currentSelection.longitude))
                .title(currentSelection.toString())
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currentSelection.latitude, currentSelection.longitude), zoomLevel))
    }

    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                activity.getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode)
        {
             PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
    }
}