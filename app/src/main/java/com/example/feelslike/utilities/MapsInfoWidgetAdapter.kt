package com.example.feelslike.utilities

import android.app.Activity
import android.view.View
import com.example.feelslike.databinding.WidgetMapsInfoBinding
import com.example.feelslike.view.MapsFragment
import com.example.feelslike.view_model.RecyclerViewFavoritesViewModel
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
        when (marker.tag)
        {
            is MapsFragment.PlaceInfo ->
            {
                imageView.setImageBitmap(
                    (marker.tag as MapsFragment.PlaceInfo).image)
            }
            is RecyclerViewFavoritesViewModel.FavoriteMarkerView ->
            {
                val favoriteView = marker.tag as
                        RecyclerViewFavoritesViewModel.FavoriteMarkerView
            }
        }
        return binding.root
    }
}