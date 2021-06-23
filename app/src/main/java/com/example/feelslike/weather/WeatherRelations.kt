package edu.dixietech.parksplus.greaterzion.model.entity.weather

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherLocationWithCurrentlyData(
		@Embedded val weather: WeatherLocation,
		@Relation(
				parentColumn = "location_id",
				entityColumn = "location_id"
		)
		val currentlyData: WeatherCurrentlyData
)

data class WeatherLocationWithHourlyData(
		@Embedded val weather: WeatherLocation,
		@Relation(
				entity = WeatherHourlyData::class,
				parentColumn = "location_id",
				entityColumn = "location_id"
		)
		val weatherHourlyWithData: WeatherHourlyWithData
)

data class WeatherLocationWithDailyData(
		@Embedded val weather: WeatherLocation,
		@Relation(
				entity = WeatherDailyData::class,
				parentColumn = "location_id",
				entityColumn = "location_id"
		)
		val weatherDailyData: WeatherDailyWithData
)

data class WeatherHourlyWithData(
		@Embedded val weatherHourly: WeatherHourly,
		@Relation(
				parentColumn = "location_id",
				entityColumn = "location_id"
		)
		val data: List<WeatherHourlyData>
)

data class WeatherDailyWithData(
		@Embedded val weatherDaily: WeatherDaily,
		@Relation(
				parentColumn = "location_id",
				entityColumn = "location_id"
		)
		val data: List<WeatherDailyData>
)