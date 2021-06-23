package com.example.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feelslike.model.entity.weather.Main


@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherResults(weather : Main)

//    @get:Query("SELECT * FROM weather_results WHERE ID = 0")
//    val weatherLiveData : LiveData<WeatherEntity?>
}