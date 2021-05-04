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

    @ColumnInfo(name = "first_name")
    val firstName : String,

    @ColumnInfo(name = "last_name")
    val lastName : String,

    @ColumnInfo(name = "email_address")
    val emailAddress : String,

    @ColumnInfo(name = "preferred_temp_set")
    val preferredTempSet : Int,

    @ColumnInfo(name = "favorites")
    val favorites : Boolean,

    @ColumnInfo(name = "height_feet")
    val heightFeet : Int,

    @ColumnInfo(name = "height_inches")
    val heightInches : Int,

    @ColumnInfo(name = "weight")
    val weight : Float,

    @ColumnInfo(name = "clothing")
    val clothing : Float,

    @ColumnInfo(name = "date")
    val date : Date,

    @ColumnInfo(name = "time_of_day")
    val timeOfDay : Time,

    @ColumnInfo(name = "latitude")
    val latitude : Double,

    @ColumnInfo(name = "longitude")
    val longitude : Double,

    @ColumnInfo(name = "activity_level")
    val activityLevel : Float
) : Serializable