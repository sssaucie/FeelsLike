package edu.dixietech.parksplus.greaterzion.model.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "weather_location")
@JsonClass(generateAdapter = true)
data class WeatherLocation(
		@PrimaryKey
		var location_id: Int = 0,
		val latitude: Double,
		val longitude: Double,
		val timezone: String
)
