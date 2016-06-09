package dmcs.astroWeather.util;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class Unit {

    public static String SPEED_UNIT = "km/h";
    public static String PRESSURE_UNIT = "mb";
    public static String TEMPERATURE_UNIT = "°C";

    double convertKilometerToMiles(double kilometer) {
        return kilometer * 0.621371;
    }

    double convertMileToKilometer(double mile) {
        return mile * 1.60934;
    }

    double convertCelsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}
