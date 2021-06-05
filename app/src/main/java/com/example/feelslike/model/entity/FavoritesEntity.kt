package com.example.feelslike.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="favorite_list")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var favorites_entity_id : Long? = null,

    val favorite_id : String? = null,

    val place_name : String = "",

    val place_lat : Double = 0.0,

    val place_lon : Double = 0.0
) : Serializable