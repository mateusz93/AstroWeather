package dmcs.astroWeather;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;

import dmcs.astroWeather.util.Parameter;

/**
 * Created by Mateusz on 2016-05-10.
 */
public class Moon {

    private AstroCalculator astroCalculator;
    private AstroCalculator.MoonInfo moonInfo;

    public Moon() {
        Calendar calendar = Calendar.getInstance();
        AstroDateTime astroDateTime = new AstroDateTime();
        astroDateTime.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        astroDateTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        astroDateTime.setMinute(calendar.get(Calendar.MINUTE));
        astroDateTime.setMonth(calendar.get(Calendar.MONTH) + 1);
        astroDateTime.setSecond(calendar.get(Calendar.SECOND));
        astroDateTime.setYear(calendar.get(Calendar.YEAR));
        astroDateTime.setTimezoneOffset((calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / (3600 * 1000));
        astroDateTime.setDaylightSaving(true);

        AstroCalculator.Location location = new AstroCalculator.Location(Parameter.latitute, Parameter.longitude);
        astroCalculator = new AstroCalculator(astroDateTime, location);
        astroCalculator.setDateTime(astroDateTime);
        astroCalculator.setLocation(location);

        moonInfo = astroCalculator.getMoonInfo();
    }

    public double getAge() {
        return moonInfo.getAge();
    }

    public double getIllumination() {
        return moonInfo.getIllumination();
    }

    public AstroDateTime getMoonrise() {
        return moonInfo.getMoonrise();
    }

    public AstroDateTime getMoonset() {
        return moonInfo.getMoonset();
    }

    public AstroDateTime getNextFullMoon() {
        return moonInfo.getNextFullMoon();
    }

    public AstroDateTime getNextNewMoon() {
        return moonInfo.getNextNewMoon();
    }

}
