package dmcs.astroWeather.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import dmcs.astroWeather.R;
import dmcs.astroWeather.db.DBLocalization;
import dmcs.astroWeather.db.Localization;
import dmcs.astroWeather.util.Parameter;
import dmcs.astroWeather.util.UnitConverter;
import dmcs.astroWeather.util.WeatherDownloader;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class WeatherFragment extends Fragment {

    private Thread thread;
    private DBLocalization db;

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
        db = new DBLocalization(getContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void setTextViews(final View rootView) {
        try {
            JSONObject weather = null;
            if (isWeatherCurrent()) {
                weather = getWeatherFromDB();
            } else {
                try {
                    weather = WeatherDownloader.getWeatherByLatitudeAndLongitude(String.valueOf(Parameter.LOCALIZATION_LATITUDE), String.valueOf(Parameter.LOCALIZATION_LONGITUDE));
                    saveToDB(weather);
                } catch (IOException | JSONException e) {
                    weather = getWeatherFromDB();
                }
            }

            String city = weather.getJSONObject("location").getString("city");
            String latitude = String.valueOf(Parameter.LOCALIZATION_LATITUDE);
            String longitude = String.valueOf(Parameter.LOCALIZATION_LONGITUDE);
            String temperature = weather.getJSONObject("item").getJSONObject("condition").getString("temp");
            String localTime = weather.getString("lastBuildDate");
            String windDirection = weather.getJSONObject("wind").getString("direction");
            String windSpeed = weather.getJSONObject("wind").getString("speed");
            String atmosphereHumidity = weather.getJSONObject("atmosphere").getString("humidity");
            String atmosphereVisibility = weather.getJSONObject("atmosphere").getString("visibility");
            String atmospherePressure = weather.getJSONObject("atmosphere").getString("pressure");
            String description = weather.getJSONObject("item").getString("description");
            if (Parameter.SPEED_UNIT.equals("mi/h")) {
                windSpeed = String.valueOf(UnitConverter.convertKilometerToMiles(Double.valueOf(windSpeed)));
            }
            if (Parameter.TEMPERATURE_UNIT.equals("°K")) {
                temperature = String.valueOf(UnitConverter.convertCelsiusToKelvin(Double.valueOf(temperature)));
            }
            ImageView weatherIcon = (ImageView) rootView.findViewById(R.id.weatherIcon);
            String iconNumber = getIconNumberFromDescription(description);
            int id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
            weatherIcon.setImageResource(id);

            description = getCurrentConditionFromDescription(description);
            localTime = getFormattedTime(localTime);

            TextView cityView = (TextView) rootView.findViewById(R.id.weatherCity);
            cityView.setText(getString(R.string.weather_city) + ": ");
            TextView cityValueView = (TextView) rootView.findViewById(R.id.weatherCityValue);
            cityValueView.setText(city);

            TextView latitudeView = (TextView) rootView.findViewById(R.id.weatherLatitude);
            latitudeView.setText(getString(R.string.weather_latitude) + ": ");
            TextView latitudeValueView = (TextView) rootView.findViewById(R.id.weatherLatitudeValue);
            latitudeValueView.setText(latitude);

            TextView longitudeView = (TextView) rootView.findViewById(R.id.weatherLongitude);
            longitudeView.setText(getString(R.string.weather_longitude) + ": ");
            TextView longitudeValueView = (TextView) rootView.findViewById(R.id.weatherLongitudeValue);
            longitudeValueView.setText(longitude);

            TextView temperatureView = (TextView) rootView.findViewById(R.id.weatherTemperature);
            temperatureView.setText(getString(R.string.weather_temperature) + ": ");
            TextView temperatureViewView = (TextView) rootView.findViewById(R.id.weatherTemperatureValue);
            temperatureViewView.setText(temperature + Parameter.TEMPERATURE_UNIT);

            TextView localTimeView = (TextView) rootView.findViewById(R.id.weatherLocalTime);
            localTimeView.setText(getString(R.string.weather_localTime) + ": ");
            TextView localTimeValueView = (TextView) rootView.findViewById(R.id.weatherLocalTimeValue);
            localTimeValueView.setText(localTime);

            TextView windDirectionView = (TextView) rootView.findViewById(R.id.weatherWindDirection);
            windDirectionView.setText(getString(R.string.weather_windDirection) + ": ");
            TextView windDirectionValueView = (TextView) rootView.findViewById(R.id.weatherWindDirectionValue);
            windDirectionValueView.setText(windDirection);

            TextView windSpeedView = (TextView) rootView.findViewById(R.id.weatherWindSpeed);
            windSpeedView.setText(getString(R.string.weather_windSpeed) + ": ");
            TextView windSpeedValueView = (TextView) rootView.findViewById(R.id.weatherWindSpeedValue);
            windSpeedValueView.setText(windSpeed + Parameter.SPEED_UNIT);

            TextView atmosphereHumidityView = (TextView) rootView.findViewById(R.id.weatherAtmosphereHumidity);
            atmosphereHumidityView.setText(getString(R.string.weather_atmosphereHumidity) + ": ");
            TextView atmosphereHumidityValueView = (TextView) rootView.findViewById(R.id.weatherAtmosphereHumidityValue);
            atmosphereHumidityValueView.setText(atmosphereHumidity + "%");

            TextView atmosphereVisibilityView = (TextView) rootView.findViewById(R.id.weatherAtmosphereVisibility);
            atmosphereVisibilityView.setText(getString(R.string.weather_atmosphereVisibility) + ": ");
            TextView atmosphereVisibilityValueView = (TextView) rootView.findViewById(R.id.weatherAtmosphereVisibilityValue);
            atmosphereVisibilityValueView.setText(atmosphereVisibility);

            TextView atmospherePressureView = (TextView) rootView.findViewById(R.id.weatherAtmospherePressure);
            atmospherePressureView.setText(getString(R.string.weather_atmospherePressure) + ": ");
            TextView atmospherePressureValueView = (TextView) rootView.findViewById(R.id.weatherAtmospherePressureValue);
            atmospherePressureValueView.setText(atmospherePressure + Parameter.PRESSURE_UNIT);

            TextView descriptionView = (TextView) rootView.findViewById(R.id.weatherDescription);
            descriptionView.setText(getString(R.string.weather_description) + ": ");
            TextView descriptionValueView = (TextView) rootView.findViewById(R.id.weatherDescriptionValue);
            descriptionValueView.setText(description);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void saveToDB(JSONObject weather) {
        try {
            Localization localization = db.findLocationByName(Parameter.LOCALIZATION_NAME);
            localization.setWeather(weather.toString());
            localization.setForecast(weather.getJSONObject("item").getJSONArray("forecast").toString());
            localization.setLastUpdate(String.valueOf(new Date().getTime()));
            db.updateLocation(localization);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getWeatherFromDB() {
        Localization localization = db.findLocationByName(Parameter.LOCALIZATION_NAME);
        if (localization.getWeather() != null) {
            try {
                return new JSONObject(localization.getWeather());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean isWeatherCurrent() {
        Localization localization = db.findLocationByName(Parameter.LOCALIZATION_NAME);
        long oneHour = 3_600_000;
        long now = new Date().getTime();
        if (localization.getLastUpdate() != null) {
            long localizationTime = Long.valueOf(localization.getLastUpdate());
            if (localizationTime + oneHour > now) {
                return true;
            }
        }
        return false;
    }

    private String getFormattedTime(String localTime) {
        String time[] = localTime.split("\\s+");
        String formattedTime = "";
        for (int i = 0; i < time.length -2; ++i) {
            formattedTime += time[i] + " ";
        }
        return formattedTime.trim();
    }

    private String getCurrentConditionFromDescription(String description) {
        int firstIndex = description.indexOf("Current Conditions:");
        int lastIndex = description.indexOf("Forecast");
        description = description.substring(firstIndex + 19, lastIndex);
        description = description.replaceAll("</b>", "");
        description = description.replaceAll("<b>", "");
        description = description.replaceAll("<BR />", "");
        description = description.replaceAll(":", "");
        return description.trim();
    }

    private String getIconNumberFromDescription(String description) {
        for (int i = 48; i >= 0; --i) {
            if (description.contains("" + i + ".gif")) {
                return "" + i;
            }
        }
        return "3200";
    }
}
