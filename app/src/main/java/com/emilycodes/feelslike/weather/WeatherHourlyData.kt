package edu.dixietech.parksplus.greaterzion.model.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "weather_hourly_data")
@JsonClass(generateAdapter = true)
data class WeatherHourlyData(
        var location_id: Int = 0,
        val time: Long?,
        val summary: String?,
        val icon: String?,
        val precipIntensity: Double?,
        val precipProbability: Double?,
        val temperature: Double?,
        val apparentTemperature: Double?,
        val dewPoint: Double?,
        val humidity: Double?,
        val pressure: Double?,
        val windSpeed: Double?,
        val windGust: Double?,
        val windBearing: Int?,
        val cloudCover: Double?,
        val uvIndex: Int?,
        val visibility: Double?,
        val ozone: Double?
) : Serializable {
    @PrimaryKey
    var id: String = ""
}