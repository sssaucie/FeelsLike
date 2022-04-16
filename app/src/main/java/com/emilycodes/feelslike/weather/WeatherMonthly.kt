package edu.dixietech.parksplus.greaterzion.model.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "weather_monthly")
@JsonClass(generateAdapter = true)
data class WeatherMonthly(
	val location_id: Int,
	val month : Int,
	val average_weather: String,
	val average_high: Float,
	val average_low: Float,
	val rainfall: Float,
	val rainfall_days: Float,
	val snowfall: Float,
	val snowfall_days: Float,
	val daylight: Float,
	val content: String
): Serializable
{
	@PrimaryKey(autoGenerate = true)
	var id: Int = 0
}
