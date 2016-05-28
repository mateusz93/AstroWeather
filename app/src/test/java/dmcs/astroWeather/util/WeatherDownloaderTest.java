package dmcs.astroWeather.util;

import org.json.JSONObject;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
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

    @Test
    public void getWeatherByWoeid() {
        String woeid = "505120";
        JSONObject json = WeatherDownloader.getWeatherByWoeid(woeid);
        assertTrue(json != null);
    }

    @Test
    public void getWeatherByLatitudeAndLongitude() {
        String latitude = "51.756619";
        String longitude = "18.087696";
        JSONObject json = WeatherDownloader.getWeatherByLatitudeAndLongitude(latitude, longitude);
        assertTrue(json != null);
    }

}