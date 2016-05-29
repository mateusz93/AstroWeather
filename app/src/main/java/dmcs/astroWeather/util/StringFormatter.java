package dmcs.astroWeather.util;

/**
 * Created by Mateusz on 2016-05-29.
 */
public class StringFormatter {

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }
}
