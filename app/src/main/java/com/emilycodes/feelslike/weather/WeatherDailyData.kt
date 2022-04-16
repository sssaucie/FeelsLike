package edu.dixietech.parksplus.greaterzion.model.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "weather_data")
@JsonClass(generateAdapter = true)
data class WeatherDailyData(
		var location_id: Int = 0,
		val time: Long?,
		val summary: String?,
		val icon: String?,
		val sunriseTime: Long?,
		val sunsetTime: Long?,
		val moonPhase: Double?,
		val precipIntensity: Double?,
		val precipIntensityMax: Double?,
		val precipIntensityMaxTime: Long?,
		val precipProbability: Double?,
		val temperatureHigh: Double?,
		val temperatureHighTime: Long?,
		val temperatureLow: Double?,
		val temperatureLowTime: Long?,
		val apparentTemperatureHigh: Double?,
		val apparentTemperatureHighTime: Long?,
		val apparentTemperatureLow: Double?,
		val apparentTemperatureLowTime: Long?,
		val dewPoint: Double?,
		val humidity: Double?,
		val pressure: Double?,
		val windSpeed: Double?,
		val windGust: Double?,
		val windGustTime: Long?,
		val windBearing: Int?,
		val cloudCover: Double?,
		val uvIndex: Int?,
		val uvIndexTime: Long?,
		val visibility: Double?,
		val ozone: Double?,
		val temperatureMin: Double?,
		val temperatureMinTime: Long?,
		val temperatureMax: Double?,
		val temperatureMaxTime: Long?,
		val apparentTemperatureMin: Double?,
		val apparentTemperatureMinTime: Long?,
		val apparentTemperatureMax: Double?,
		val apparentTemperatureMaxTime: Long?
) : Serializable {
	@PrimaryKey
	var id: String = ""
}