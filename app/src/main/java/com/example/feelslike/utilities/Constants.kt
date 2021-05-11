package com.example.feelslike.utilities

import com.example.feelslike.model.entity.FeelsLikeEntity

/**
 * Constants used throughout the app
 */

const val DATABASE_NAME = "feels_like_db"
const val FEELS_LIKE_DATA_FILENAME = "feels_like.json"

const val FIRST_RUN_KEY = "first_run"
const val SHARED_PREFS_KEY = "shared_prefs"

const val CELSIUS_TO_KELVIN = 273.15F
const val CP_VAPOR = 1805.0F // Specific heat capacity water vapor
const val CP_WATER = 4186 // Specific heat capacity water liquid
const val CP_AIR = 1004 // Specific heat capacity water in air
const val CONDENSATE_WATER_HEAT_ABSORPTION = 2501000 // Heat that water
                                                    // absorbs to change to steam
const val DRY_BULB_AIR_TEMP = 27F
const val MEAN_RADIANT_TEMP = 25
const val AVERAGE_AIR_VELOCITY = 0.1F
const val RELATIVE_HUMIDITY = 50 // %
const val EULERS_NUMBER = 2.71828 // The number e, sometimes called the natural number

object Constants{
    val DUMMY_INPUT = FeelsLikeEntity(0,"Jon","Doe","example_email.@email.com",72F,25F,false,5,8F,2F,83,160F,1.2F,"1/20/40","1400",25.0,-25.0,1.3F)
}

val MET_TYPICAL_TASKS = activityMap.values