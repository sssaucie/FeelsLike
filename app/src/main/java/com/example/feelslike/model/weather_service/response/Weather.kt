package com.example.feelslike.model.weather_service.response

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)