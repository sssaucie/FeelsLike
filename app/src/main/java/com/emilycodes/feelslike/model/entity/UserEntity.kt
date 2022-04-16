package com.emilycodes.feelslike.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_entity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var user_entity_id : Long? = null,

    val user_id : Long? = null,

    var first_name : String = "",

    var last_name : String = "",

    var email_address : String = "",

    val is_metric : Int = 0,

    var preferred_temp : Float = 0.0F,

    var height : Float = 0.0F,

    var weight : Float = 0.0F
    ) : Serializable