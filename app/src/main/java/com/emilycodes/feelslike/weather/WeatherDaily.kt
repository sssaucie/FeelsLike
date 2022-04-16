package edu.dixietech.parksplus.greaterzion.model.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "weather_daily")
@JsonClass(generateAdapter = true)
data class WeatherDaily(
        @PrimaryKey
        var location_id: Int = 0,
        val summary: String?,
        val icon: String?
) : Serializable
