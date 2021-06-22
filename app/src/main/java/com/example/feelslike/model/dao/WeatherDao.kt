package com.example.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feelslike.model.entity.CURRENT_WEATHER_ID
import com.example.feelslike.model.entity.WeatherResponseEntity
import com.example.feelslike.model.weather_service.ImperialUnitWeatherResponseEntry
import com.example.feelslike.model.weather_service.MetricUnitWeatherResponseEntry

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherResponseEntity: WeatherResponseEntity)

    @Query("SELECT * FROM weather_response WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric() : LiveData<MetricUnitWeatherResponseEntry>

    @Query("SELECT * FROM weather_response WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial() : LiveData<ImperialUnitWeatherResponseEntry>
}