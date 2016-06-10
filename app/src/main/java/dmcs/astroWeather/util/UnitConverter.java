package dmcs.astroWeather.util;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class UnitConverter {

    public static double convertKilometerToMiles(double kilometer) {
        return kilometer * 0.621371;
    }

    public static double convertMileToKilometer(double mile) {
        return mile * 1.60934;
    }

    public static double convertCelsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    public static double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}
