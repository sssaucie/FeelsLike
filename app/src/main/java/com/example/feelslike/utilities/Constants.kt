package com.example.feelslike.utilities

/**
 * Constants used throughout the app
 */

const val USER_DATABASE_NAME = "user_db"
const val STANDARD_USER_DATA_FILENAME = "standardUser.json"

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

val MET_TYPICAL_TASKS = activityMap.values