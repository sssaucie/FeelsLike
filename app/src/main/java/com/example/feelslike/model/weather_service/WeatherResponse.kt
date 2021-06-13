package com.example.feelslike.model.weather_service

data class WeatherResponse(
    val coord : Coordinates,
    val weather : List<WeatherParams>,
    val main : BaseWeatherStats
)
{
    data class Coordinates(
        val lon : Double,
        val lat : Double
    )

    data class WeatherParams(
        val id : Int,
        val main : String,
        val description : String,
    )

    data class BaseWeatherStats(
        val temp : Float,
        val feels_like : Float,
        val temp_min : Double,
        val temp_max : Double,
        val pressure : Int,
        val humidity : Int
    )
}