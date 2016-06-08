package dmcs.astroWeather.util;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class UnitsConverter {

    double kilometerToMiles(double kilometer) {
        return kilometer * 0.621371;
    }

    double mileToKilometer(double mile) {
        return mile * 1.60934;
    }

    double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}
