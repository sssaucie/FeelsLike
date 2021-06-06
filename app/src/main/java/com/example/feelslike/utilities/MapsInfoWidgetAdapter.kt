package com.example.feelslike.utilities

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import com.example.feelslike.databinding.WidgetMapsInfoBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MapsInfoWidgetAdapter(context : Activity) : GoogleMap.InfoWindowAdapter
{
    private val binding = WidgetMapsInfoBinding.inflate(context.layoutInflater)

    override fun getInfoWindow(marker : Marker) : View?
    {
        return null
    }

    override fun getInfoContents(marker : Marker) : View?
    {
        binding.mapsInfoTitle.text = marker.title ?: ""
        binding.mapsInfoPhone.text = marker.snippet ?: ""
        val imageView = binding.widgetMapsInfoPhoto
        imageView.setImageBitmap((marker.tag as Bitmap))
        return binding.root
    }
}