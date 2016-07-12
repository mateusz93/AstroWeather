package dmcs.astroWeather.task;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import dmcs.astroWeather.util.WeatherDownloader;

/**
 * @Author Mateusz Wieczorek on 2016-07-12.
 */
public class DownloadWoeidByLatitudeAndLongitudeTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        try {
            return WeatherDownloader.getWoeidByLatitudeAndLongitude(params[0], params[1]);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
