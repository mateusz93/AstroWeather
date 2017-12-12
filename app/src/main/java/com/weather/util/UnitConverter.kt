package com.weather.util

/**
 * @author Mateusz Wieczorek
 */
object UnitConverter {

    fun convertMileToKilometer(mile: Double): String {
        return getFormattedNumber(mile * 1.60934)
    }

    fun convertKelvinToCelsius(kelvin: Double): String {
        return getFormattedNumber(kelvin - 273.0)
    }

    fun getFormattedNumber(number: Double): String {
        var number = number
        number = java.lang.Double.valueOf(String.format("%.2f", number))!! // set precision
        return if (number == number.toLong().toDouble()) {
            String.format("%d", number.toLong())
        } else {
            String.format("%s", number)
        }
    }
}
