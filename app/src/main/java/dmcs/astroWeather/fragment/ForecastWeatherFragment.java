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

import org.json.JSONArray;
import org.json.JSONException;

import dmcs.astroWeather.R;
import dmcs.astroWeather.db.DBLocalization;
import dmcs.astroWeather.db.Localization;
import dmcs.astroWeather.util.Parameter;
import dmcs.astroWeather.util.UnitConverter;

/**
 * @Author Mateusz Wieczorek on 2016-06-08.
 */
public class ForecastWeatherFragment extends Fragment {

    private DBLocalization dbLocalization;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ForecastWeatherFragment newInstance() {
        ForecastWeatherFragment fragment = new ForecastWeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forecast_weather_fragment, container, false);
        createThread(rootView);
        //thread.start();
        return rootView;
    }

    @NonNull
    private void createThread(final View rootView) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setValues(rootView);
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
        dbLocalization = new DBLocalization(getContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void setValues(final View rootView) {
        try {
            JSONArray weatherForecast = getWeatherForecastFromDB();
            setIcons(rootView, weatherForecast);
            setDates(rootView, weatherForecast);
            setTemperatures(rootView, weatherForecast);
            setDescriptions(rootView, weatherForecast);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private JSONArray getWeatherForecastFromDB() {
        Localization localization = dbLocalization.findLocationByName(Parameter.LOCALIZATION_NAME);
        if (localization.getForecast() != null) {
            try {
                return new JSONArray(localization.getForecast());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void setDescriptions(View rootView, JSONArray weatherForecast) {
        TextView forecastDescription1 = (TextView) rootView.findViewById(R.id.forecastDay1DescriptionValue);
        forecastDescription1.setText(getForecastDescription(weatherForecast, 1));

        TextView forecastDescription2 = (TextView) rootView.findViewById(R.id.forecastDay2DescriptionValue);
        forecastDescription2.setText(getForecastDescription(weatherForecast, 2));

        TextView forecastDescription3 = (TextView) rootView.findViewById(R.id.forecastDay3DescriptionValue);
        forecastDescription3.setText(getForecastDescription(weatherForecast, 3));

        TextView forecastDescription4 = (TextView) rootView.findViewById(R.id.forecastDay4DescriptionValue);
        forecastDescription4.setText(getForecastDescription(weatherForecast, 4));

        TextView forecastDescription5 = (TextView) rootView.findViewById(R.id.forecastDay5DescriptionValue);
        forecastDescription5.setText(getForecastDescription(weatherForecast, 5));

        TextView forecastDescription6 = (TextView) rootView.findViewById(R.id.forecastDay6DescriptionValue);
        forecastDescription6.setText(getForecastDescription(weatherForecast, 6));

        TextView forecastDescription7 = (TextView) rootView.findViewById(R.id.forecastDay7DescriptionValue);
        forecastDescription7.setText(getForecastDescription(weatherForecast, 7));
    }

    private void setTemperatures(View rootView, JSONArray weatherForecast) {
        TextView forecastTemperature1 = (TextView) rootView.findViewById(R.id.forecastDay1TemperatureValue);
        forecastTemperature1.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 1))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 1)) + Parameter.TEMPERATURE_UNIT);

        TextView forecastTemperature2 = (TextView) rootView.findViewById(R.id.forecastDay2TemperatureValue);
        forecastTemperature2.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 2))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 2)) + Parameter.TEMPERATURE_UNIT);

        TextView forecastTemperature3 = (TextView) rootView.findViewById(R.id.forecastDay3TemperatureValue);
        forecastTemperature3.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 3))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 3)) + Parameter.TEMPERATURE_UNIT);

        TextView forecastTemperature4 = (TextView) rootView.findViewById(R.id.forecastDay4TemperatureValue);
        forecastTemperature4.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 4))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 4)) + Parameter.TEMPERATURE_UNIT);

        TextView forecastTemperature5 = (TextView) rootView.findViewById(R.id.forecastDay5TemperatureValue);
        forecastTemperature5.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 5))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 5)) + Parameter.TEMPERATURE_UNIT);

        TextView forecastTemperature6 = (TextView) rootView.findViewById(R.id.forecastDay6TemperatureValue);
        forecastTemperature6.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 6))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 6)) + Parameter.TEMPERATURE_UNIT);

        TextView forecastTemperature7 = (TextView) rootView.findViewById(R.id.forecastDay7TemperatureValue);
        forecastTemperature7.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 7))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 7)) + Parameter.TEMPERATURE_UNIT);
    }

    private void setDates(View rootView, JSONArray weatherForecast) {
        TextView forecastDate1 = (TextView) rootView.findViewById(R.id.forecastDay1DateValue);
        forecastDate1.setText(getForecastDay(weatherForecast, 1) + ", " + getForecastDate(weatherForecast, 1));

        TextView forecastDate2 = (TextView) rootView.findViewById(R.id.forecastDay2DateValue);
        forecastDate2.setText(getForecastDay(weatherForecast, 2) + ", " + getForecastDate(weatherForecast, 2));

        TextView forecastDate3 = (TextView) rootView.findViewById(R.id.forecastDay3DateValue);
        forecastDate3.setText(getForecastDay(weatherForecast, 3) + ", " + getForecastDate(weatherForecast, 3));

        TextView forecastDate4 = (TextView) rootView.findViewById(R.id.forecastDay4DateValue);
        forecastDate4.setText(getForecastDay(weatherForecast, 4) + ", " + getForecastDate(weatherForecast, 4));

        TextView forecastDate5 = (TextView) rootView.findViewById(R.id.forecastDay5DateValue);
        forecastDate5.setText(getForecastDay(weatherForecast, 5) + ", " + getForecastDate(weatherForecast, 5));

        TextView forecastDate6 = (TextView) rootView.findViewById(R.id.forecastDay6DateValue);
        forecastDate6.setText(getForecastDay(weatherForecast, 6) + ", " + getForecastDate(weatherForecast, 6));

        TextView forecastDate7 = (TextView) rootView.findViewById(R.id.forecastDay7DateValue);
        forecastDate7.setText(getForecastDay(weatherForecast, 7) + ", " + getForecastDate(weatherForecast, 7));
    }

    private void setIcons(View rootView, JSONArray weatherForecast) {
        ImageView forecastIcon1 = (ImageView) rootView.findViewById(R.id.forecastDay1Icon);
        String iconNumber = getForecastIconNumber(weatherForecast, 1);
        int id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon1.setImageResource(id);

        ImageView forecastIcon2 = (ImageView) rootView.findViewById(R.id.forecastDay2Icon);
        iconNumber = getForecastIconNumber(weatherForecast, 2);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon2.setImageResource(id);

        ImageView forecastIcon3 = (ImageView) rootView.findViewById(R.id.forecastDay3Icon);
        iconNumber = getForecastIconNumber(weatherForecast, 3);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon3.setImageResource(id);

        ImageView forecastIcon4 = (ImageView) rootView.findViewById(R.id.forecastDay4Icon);
        iconNumber = getForecastIconNumber(weatherForecast, 4);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon4.setImageResource(id);

        ImageView forecastIcon5 = (ImageView) rootView.findViewById(R.id.forecastDay5Icon);
        iconNumber = getForecastIconNumber(weatherForecast, 5);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon5.setImageResource(id);

        ImageView forecastIcon6 = (ImageView) rootView.findViewById(R.id.forecastDay6Icon);
        iconNumber = getForecastIconNumber(weatherForecast, 6);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon6.setImageResource(id);

        ImageView forecastIcon7 = (ImageView) rootView.findViewById(R.id.forecastDay7Icon);
        iconNumber = getForecastIconNumber(weatherForecast, 7);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon7.setImageResource(id);
    }

    private String getForecastIconNumber(JSONArray weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "code");
    }

    private String getForecastDate(JSONArray weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "date");
    }

    private String getForecastDay(JSONArray weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "day");
    }

    private String getForecastHighTemperature(JSONArray weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "high");
    }

    private String getForecastLowTemperature(JSONArray weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "low");
    }

    private String getForecastDescription(JSONArray weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "text");
    }

    private String getForecastItem(JSONArray weather, int dayNumber, String item) {
        try {
            return weather.getJSONObject(dayNumber).getString(item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getConverterTemperature(String temperature) {
        if (Parameter.TEMPERATURE_UNIT.equals("°K")) {
            return UnitConverter.convertCelsiusToKelvin(Double.valueOf(temperature));
        }
        return temperature;
    }

}
