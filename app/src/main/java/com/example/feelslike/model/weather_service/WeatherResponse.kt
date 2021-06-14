package com.example.feelslike.model.weather_service

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherResponse(
    val coord : Coordinates,
    val weather : List<WeatherParams>,
    val main : BaseWeatherStats
) : Parcelable
{
    @Parcelize
    data class Coordinates(
        val lon : Double,
        val lat : Double
    ) : Parcelable
    {
        val hasCoordinates
            get() = LatLng(lat, lon)
    }

    @Parcelize
    data class WeatherParams(
        val id : Int,
        val main : String,
        val description : String
    ) : Parcelable

    @Parcelize
    data class BaseWeatherStats(
        val temp : Float,
        val feels_like : Float,
        val temp_min : Double,
        val temp_max : Double,
        val pressure : Int,
        val humidity : Int
    ) : Parcelable
}