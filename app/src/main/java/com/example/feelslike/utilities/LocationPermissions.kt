package com.example.feelslike.utilities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.feelslike.view.BaseMapsFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class LocationPermissions : AppCompatActivity()
{
    private lateinit var context : Context
    private lateinit var map : GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private val TAG = "LocationPermissions"

    private fun requestLocationPermission()
    {
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION), BaseMapsFragment.REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode : Int,
        permissions : Array<out String>,
        grantResults : IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BaseMapsFragment.REQUEST_LOCATION)
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

    fun getCurrentLocation()
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
}