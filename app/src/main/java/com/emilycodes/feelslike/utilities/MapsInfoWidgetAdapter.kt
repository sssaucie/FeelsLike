package com.emilycodes.feelslike.utilities

import android.app.Activity
import android.view.View
import com.emilycodes.feelslike.databinding.WidgetMapsInfoBinding
import com.emilycodes.feelslike.view_model.LandingPageViewModel
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
            is MapsService.PlaceInfo ->
            {
                imageView.setImageBitmap(
                    (marker.tag as MapsService.PlaceInfo).image)
            }
            is LandingPageViewModel.FavoritesMarkerView ->
            {
                val favoriteView = marker.tag as
                        LandingPageViewModel.FavoritesMarkerView
                imageView.setImageBitmap(favoriteView.getImage(context))
            }
        }
        return binding.root
    }
}