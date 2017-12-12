package com.weather.util

/**
 * @author Mateusz Wieczorek
 */
object UnitConverter {

    fun pressureMbToHpa(kelvin: Double): String {
        //TODO
        return ""
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
