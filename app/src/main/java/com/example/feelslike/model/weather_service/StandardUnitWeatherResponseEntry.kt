package com.example.feelslike.model.weather_service

interface StandardUnitWeatherResponseEntry
{
    val temperature: Double
    val minTemp: Double
    val maxTemp: Double
    val conditionText: String
    val barometricReading: Int
    val humidity: Int
    val windSpeed: Double
    val windDirection: Int
    val feelsLikeTemperature: Double
    val visibilityDistance: Int
}