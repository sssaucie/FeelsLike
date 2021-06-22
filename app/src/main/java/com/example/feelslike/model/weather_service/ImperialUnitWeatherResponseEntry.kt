package com.example.feelslike.model.weather_service

import androidx.room.ColumnInfo

data class ImperialUnitWeatherResponseEntry(
    @ColumnInfo(name = "main_temp")
    override val temperature: Double,
    @ColumnInfo(name = "main_temp_min")
    override val minTemp: Double,
    @ColumnInfo(name = "main_temp_max")
    override val maxTemp: Double,
    @ColumnInfo(name = "list_weather_description")
    override val conditionText: String,
    @ColumnInfo(name = "main_pressure")
    override val barometricReading: Int,
    @ColumnInfo(name = "main_humidity")
    override val humidity: Int,
    @ColumnInfo(name = "wind_speed")
    override val windSpeed: Double,
    @ColumnInfo(name = "wind_deg")
    override val windDirection: Int,
    @ColumnInfo(name = "main_feels_like")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "visibility")
    override val visibilityDistance: Int
) : StandardUnitWeatherResponseEntry