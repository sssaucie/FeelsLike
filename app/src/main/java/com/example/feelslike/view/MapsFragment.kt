package com.example.feelslike.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.feelslike.R
import com.example.feelslike.model.entity.CalculationsEntity
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

    // A default location (Sydney, Australia) to use when location permission is not granted
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

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
        currentSelection = MapsFragmentArgs.fromBundle(requireArguments()).currentSelection
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val zoomLevel = 7.5F
        map.addMarker(
            MarkerOptions()
                .position(LatLng(currentSelection.latitude, currentSelection.longitude))
                .title(currentSelection.toString())
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currentSelection.latitude, currentSelection.longitude), zoomLevel))
    }
}