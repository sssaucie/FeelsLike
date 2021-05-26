package com.example.feelslike.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "calculations_entity")
data class CalculationsEntity(
    @PrimaryKey(autoGenerate = true)
    val calculations_id : Long = 0L,

    val clothing : Float,

    val favorites : Int,

    val latitude : Double,

    val longitude : Double,

    val activity_level : Float
) : Serializable