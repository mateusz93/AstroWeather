package dmcs.astroWeather;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

/**
 * Created by Mateusz on 2016-05-10.
 */
public class Sun {

    private AstroCalculator astroCalculator;
    private AstroCalculator.SunInfo sunInfo;

    public Sun() {
        sunInfo = astroCalculator.getSunInfo();
    }

    public double getAzimuthRise() {
        return sunInfo.getAzimuthRise();
    }

    public double getAzimuthSet() {
        return sunInfo.getAzimuthSet();
    }

    public AstroDateTime getSunrise() {
        return sunInfo.getSunrise();
    }

    public AstroDateTime getSunset() {
        return sunInfo.getSunset();
    }

    public AstroDateTime getTwilightEvening() {
        return sunInfo.getTwilightEvening();
    }

    public AstroDateTime getTwilightMorning() {
        return sunInfo.getTwilightMorning();
    }

}
