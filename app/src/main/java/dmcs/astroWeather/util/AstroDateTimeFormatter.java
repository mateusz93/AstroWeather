package dmcs.astroWeather.util;

import com.astrocalculator.AstroDateTime;

/**
 * Created by Mateusz on 2016-05-11.
 */
public class AstroDateTimeFormatter {

    public static String getFormattedTime(AstroDateTime time) {
        StringBuilder formattedTime = new StringBuilder();
        formattedTime.append(String.format("%02d", time.getHour())).append(":");
        formattedTime.append(String.format("%02d", time.getMinute())).append(":");
        formattedTime.append(String.format("%02d", time.getSecond()));
        return formattedTime.toString();
    }

    public static String getFormattedDate(AstroDateTime time) {
        StringBuilder formattedTime = new StringBuilder();
        formattedTime.append(String.format("%02d", time.getDay())).append(".");
        formattedTime.append(String.format("%02d", time.getMonth())).append(".");
        formattedTime.append(String.format("%04d", time.getYear()));
        return formattedTime.toString();
    }

    public static String getFormattedDateAndTime(AstroDateTime time) {
        StringBuilder formattedTime = new StringBuilder();
        formattedTime.append(String.format("%02d", time.getDay())).append(".");
        formattedTime.append(String.format("%02d", time.getMonth())).append(".");
        formattedTime.append(String.format("%04d", time.getYear())).append("  ");
        formattedTime.append(String.format("%02d", time.getHour())).append(":");
        formattedTime.append(String.format("%02d", time.getMinute())).append(":");
        formattedTime.append(String.format("%02d", time.getSecond()));
        return formattedTime.toString();
    }

}
