package com.example.feelslike.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "calculations_entity")
data class CalculationsEntity(
    @PrimaryKey(autoGenerate = true)
    var calculations_entity_id : Long? = null,

    var calculations_id : String? = null,

    val clothing : Float = 0.0F,

    val favorites : Int = 0,

    var latitude : Double = 0.0,

    var longitude : Double = 0.0,

    val activity_level : Float = 0.0F
) : Serializable