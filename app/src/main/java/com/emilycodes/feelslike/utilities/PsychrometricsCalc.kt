package com.emilycodes.feelslike.utilities

/**
 * Breaking down the calculations for psychrometrics
 */

class PsychrometricsCalc()
{
    /**
     * Params:
     * Dry bulb air temp, in Celsius
     *
     * Returns:
     * Saturation vapor pressure in Torr
     */

    val exponent = (3.2862221374) // (18.6686 - 4030.183 / (DRY_BULB_AIR_TEMP + 235))
    val saturationVaporPressureInTorr = Math.exp(exponent)

    /**
     * Params:
     * Air velocity measured by the sensor, m/s
     * Metabolic rate
     *
     * Returns:
     * Relative air velocity, m/s
     */

    fun relativeAirVelocity() {
        // TODO: add argument to pass in selected activity value
        //  from the user to this equation (substitute activityMap.values)
        /*
        val solution = Math.round(AVERAGE_AIR_VELOCITY + 0.3 * (activityMap.values - 1), 3)
        if activityMap.values > 1 {
        return solution
        } else { return AVERAGE_AIR_VELOCITY }
         */
    }

}