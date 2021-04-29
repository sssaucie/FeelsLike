package com.example.feelslike.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "feels_like")
data class FeelsLikeEntity(
    @PrimaryKey(autoGenerate = true)
    val feelsLikeId : Long = 0L,

    val name : String
) : Serializable