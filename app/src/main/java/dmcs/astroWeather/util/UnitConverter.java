package dmcs.astroWeather.util;

/**
 * @Author Mateusz Wieczorek on 2016-06-08.
 */
public class UnitConverter {

    public static String convertKilometerToMiles(double kilometer) {
        return getFormattedNumber(kilometer * 0.621371);
    }

    public static String convertMileToKilometer(double mile) {
        return getFormattedNumber(mile * 1.60934);
    }

    public static String convertCelsiusToKelvin(double celsius) {
        return getFormattedNumber(celsius + 273.0);
    }

    public static String convertKelvinToCelsius(double kelvin) {
        return getFormattedNumber(kelvin - 273.0);
    }

    public static String getFormattedNumber(double number) {
        number = Double.valueOf(String.format("%.2f", number)); // set precision
        if(number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%s", number);
        }
    }
}
