package com.example.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feelslike.model.entity.WEATHER_LOCATION_ID
import com.example.feelslike.model.entity.WeatherLocation

@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation : WeatherLocation)

    @Query("SELECT * FROM weather_location WHERE id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>

    @Query("SELECT * FROM weather_location WHERE id = $WEATHER_LOCATION_ID")
    fun getLocationNonLive(): WeatherLocation?
}
