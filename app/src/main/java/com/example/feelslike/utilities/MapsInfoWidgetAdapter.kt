package com.example.feelslike.utilities

import android.app.Activity
import android.view.View
import com.example.feelslike.databinding.WidgetMapsInfoBinding
import com.example.feelslike.view.BaseMapsFragment
import com.example.feelslike.view_model.SharedViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MapsInfoWidgetAdapter(val context : Activity) : GoogleMap.InfoWindowAdapter
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
        when (marker.tag)
        {
            is BaseMapsFragment.PlaceInfo ->
            {
                imageView.setImageBitmap(
                    (marker.tag as BaseMapsFragment.PlaceInfo).image)
            }
            is SharedViewModel.FavoritesMarkerView ->
            {
                val favoriteView = marker.tag as
                        SharedViewModel.FavoritesMarkerView
                imageView.setImageBitmap(favoriteView.getImage(context))
            }
        }
        return binding.root
    }
}