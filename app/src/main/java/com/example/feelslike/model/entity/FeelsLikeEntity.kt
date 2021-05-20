package com.example.feelslike.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Time
import java.util.*

@Entity(tableName = "feels_like")
data class FeelsLikeEntity(
    @PrimaryKey(autoGenerate = true)
    val feelsLikeId : Long = 0L,

    val first_name : String,

    val last_name : String,

    val email_address : String,

    val preferred_temp_set_fahrenheit : Float,

    val preferred_temp_set_celsius : Float,

    val favorites : Boolean,

    val height_feet : Int,

    val height_inches : Float,

    val height_metres : Float,

    val height_centimeters : Int,

    val weight : Float,

    val clothing : Float,

    val date : String,

    val time_of_day : String,

    val latitude : Double,

    val longitude : Double,

    val activity_level : Float
) : Serializable