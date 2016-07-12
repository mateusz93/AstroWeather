package dmcs.astroWeather.task;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dmcs.astroWeather.util.WeatherDownloader;

/**
 * @Author Mateusz Wieczorek on 2016-07-12.
 */
public class WeatherDownloadByLatitudeAndLongitudeTask extends AsyncTask<String, String, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            return WeatherDownloader.getWeatherByLatitudeAndLongitude(params[0], params[1]);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
