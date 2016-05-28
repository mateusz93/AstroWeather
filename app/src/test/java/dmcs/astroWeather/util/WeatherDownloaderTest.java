package dmcs.astroWeather.util;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Mateusz on 2016-05-27.
 */
public class WeatherDownloaderTest {

    @Test
    public void getWoeid() {
        String city = "lodz";
        String country = "pl";
        String woeid = WeatherDownloader.getWoeidByCityAndCountry(city, country);
        assertThat(woeid, is("505120"));
    }

}