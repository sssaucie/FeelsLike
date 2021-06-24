package com.example.feelslike.model.entity.weather

import java.io.Serializable

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
): Serializable