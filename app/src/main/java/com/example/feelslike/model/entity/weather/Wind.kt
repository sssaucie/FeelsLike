package com.example.feelslike.model.entity.weather

import java.io.Serializable

data class Wind(
    val deg: Int,
    val speed: Double
): Serializable
