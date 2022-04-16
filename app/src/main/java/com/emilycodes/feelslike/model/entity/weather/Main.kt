package com.emilycodes.feelslike.model.entity.weather

import java.io.Serializable

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
): Serializable