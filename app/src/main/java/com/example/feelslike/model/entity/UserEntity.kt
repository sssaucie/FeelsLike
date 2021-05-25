package com.example.feelslike.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Time
import java.util.*

@Entity(tableName = "user_entity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val user_id : Long = 0L,

    val first_name : String,

    val last_name : String,

    val email_address : String,

    val is_metric : Int,

    val preferred_temp : Float,

    val height : Float,

    val weight : Float
    ) : Serializable