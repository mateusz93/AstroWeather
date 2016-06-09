package dmcs.astroWeather.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dmcs.astroWeather.R;
import dmcs.astroWeather.activity.LocalizationsActivity;
import dmcs.astroWeather.exception.IncorrectLocalizationException;
import dmcs.astroWeather.util.Parameter;
import dmcs.astroWeather.util.StringFormatter;
import dmcs.astroWeather.util.WeatherDownloader;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class WeatherFragment extends Fragment {

    private Thread thread;

    public WeatherFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WeatherFragment newInstance(int sectionNumber) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather_fragment, container, false);
        createThread(rootView);
        //thread.start();
        return rootView;
    }

    @NonNull
    private void createThread(final View rootView) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTextViews(rootView);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        //thread.interrupt();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void setTextViews(final View rootView) {
        try {
            JSONObject weather = WeatherDownloader.getWeatherByLatitudeAndLongitude(String.valueOf(Parameter.latitute), String.valueOf(Parameter.longitude));
            String city = weather.getJSONObject("location").getString("city");
            String latitude = String.valueOf(Parameter.latitute);
            String longitude = String.valueOf(Parameter.longitude);
            String localTime = weather.getString("lastBuildDate");
            String windDirection = weather.getJSONObject("wind").getString("direction");
            String windSpeed = weather.getJSONObject("wind").getString("speed");
            String atmosphereHumidity = weather.getJSONObject("atmosphere").getString("humidity");
            String atmosphereVisibility = weather.getJSONObject("atmosphere").getString("visibility");
            String atmospherePressure = weather.getJSONObject("atmosphere").getString("pressure");
            String description = weather.getJSONObject("item").getString("description");

            TextView cityView = (TextView) rootView.findViewById(R.id.weatherCity);
            cityView.setText(StringFormatter.padRight(getString(R.string.weather_city) + ": ", 30)
                    + String.format("%10s", city));

            TextView latitudeView = (TextView) rootView.findViewById(R.id.weatherLatitude);
            latitudeView.setText(StringFormatter.padRight(getString(R.string.weather_latitude) + ": ", 30)
                    + String.format("%10s", latitude));

            TextView longitudeView = (TextView) rootView.findViewById(R.id.weatherLongitude);
            longitudeView.setText(StringFormatter.padRight(getString(R.string.weather_longitude) + ": ", 30)
                    + String.format("%10s", longitude));

            TextView localTimeView = (TextView) rootView.findViewById(R.id.weatherLocalTime);
            localTimeView.setText(StringFormatter.padRight(getString(R.string.weather_localTime) + ": ", 30)
                    + String.format("%10s", localTime));

            TextView windDirectionView = (TextView) rootView.findViewById(R.id.weatherWindDirection);
            windDirectionView.setText(StringFormatter.padRight(getString(R.string.weather_windDirection) + ": ", 30)
                    + String.format("%10s", windDirection));

            TextView windSpeedView = (TextView) rootView.findViewById(R.id.weatherWindSpeed);
            windSpeedView.setText(StringFormatter.padRight(getString(R.string.weather_windSpeed) + ": ", 30)
                    + String.format("%10s", windSpeed));

            TextView atmosphereHumidityView = (TextView) rootView.findViewById(R.id.weatherAtmosphereHumidity);
            atmosphereHumidityView.setText(StringFormatter.padRight(getString(R.string.weather_atmosphereHumidity) + ": ", 30)
                    + String.format("%10s", atmosphereHumidity));

            TextView atmosphereVisibilityView = (TextView) rootView.findViewById(R.id.weatherAtmosphereVisibility);
            atmosphereVisibilityView.setText(StringFormatter.padRight(getString(R.string.weather_atmosphereVisibility) + ": ", 30)
                    + String.format("%10s", atmosphereVisibility));

            TextView atmospherePressureView = (TextView) rootView.findViewById(R.id.weatherAtmospherePressure);
            atmospherePressureView.setText(StringFormatter.padRight(getString(R.string.weather_atmospherePressure) + ": ", 30)
                    + String.format("%10s", atmospherePressure));

            TextView descriptionView = (TextView) rootView.findViewById(R.id.weatherDescription);
            descriptionView.setText(StringFormatter.padRight(getString(R.string.weather_description) + ": ", 30)
                    + String.format("%10s", description));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
