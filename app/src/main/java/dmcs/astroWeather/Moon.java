package dmcs.astroWeather;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

/**
 * Created by Mateusz on 2016-05-10.
 */
public class Moon {

    private AstroCalculator astroCalculator;
    private AstroCalculator.MoonInfo moonInfo;

    public Moon() {
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
