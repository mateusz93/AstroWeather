package dmcs.astroWeather.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
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
 * Created by Mateusz on 2016-06-08.
 */
public class ForecastWeatherFragment extends Fragment {

    private Thread thread;
    private DBLocalization db;

    public ForecastWeatherFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ForecastWeatherFragment newInstance(int sectionNumber) {
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
        JSONArray weatherForecast = getWeatherForecastFromDB();
        setIcons(rootView, weatherForecast);
        setDates(rootView, weatherForecast);
        setTemperatures(rootView, weatherForecast);
        setDescriptions(rootView, weatherForecast);
    }

    private JSONArray getWeatherForecastFromDB() {
        Localization localization = db.findLocationByName(Parameter.LOCALIZATION_NAME);
        JSONArray weatherForecast = null;
        try {
            weatherForecast = new JSONArray(localization.getForecast());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherForecast;
    }

    private void setDescriptions(View rootView, JSONArray weatherForecast) {
        final String DESCRIPTION = getResources().getString(R.string.forecast_description);

        TextView forecastDescription1 = (TextView) rootView.findViewById(R.id.forecastDay1Description);
        String boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weatherForecast, 1) + "</b>";
        forecastDescription1.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription2 = (TextView) rootView.findViewById(R.id.forecastDay2Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weatherForecast, 2) + "</b>";
        forecastDescription2.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription3 = (TextView) rootView.findViewById(R.id.forecastDay3Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weatherForecast, 3) + "</b>";
        forecastDescription3.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription4 = (TextView) rootView.findViewById(R.id.forecastDay4Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weatherForecast, 4) + "</b>";
        forecastDescription4.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription5 = (TextView) rootView.findViewById(R.id.forecastDay5Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weatherForecast, 5) + "</b>";
        forecastDescription5.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription6 = (TextView) rootView.findViewById(R.id.forecastDay6Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weatherForecast, 6) + "</b>";
        forecastDescription6.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription7 = (TextView) rootView.findViewById(R.id.forecastDay7Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weatherForecast, 7) + "</b>";
        forecastDescription7.setText(Html.fromHtml(boldedDescriptionValue));
    }

    private void setTemperatures(View rootView, JSONArray weatherForecast) {
        final String TEMPERATURE = getResources().getString(R.string.forecast_temperature);

        TextView forecastTemperature1 = (TextView) rootView.findViewById(R.id.forecastDay1Temperature);
        String boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getConverterTemperature(getForecastLowTemperature(weatherForecast, 1))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 1)) + Parameter.TEMPERATURE_UNIT + "</b>";
        forecastTemperature1.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature2 = (TextView) rootView.findViewById(R.id.forecastDay2Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getConverterTemperature(getForecastLowTemperature(weatherForecast, 2))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 2)) + Parameter.TEMPERATURE_UNIT + "</b>";
        forecastTemperature2.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature3 = (TextView) rootView.findViewById(R.id.forecastDay3Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getConverterTemperature(getForecastLowTemperature(weatherForecast, 3))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 3)) + Parameter.TEMPERATURE_UNIT + "</b>";
        forecastTemperature3.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature4 = (TextView) rootView.findViewById(R.id.forecastDay4Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getConverterTemperature(getForecastLowTemperature(weatherForecast, 4))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 4)) + Parameter.TEMPERATURE_UNIT + "</b>";
        forecastTemperature4.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature5 = (TextView) rootView.findViewById(R.id.forecastDay5Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getConverterTemperature(getForecastLowTemperature(weatherForecast, 5))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 5)) + Parameter.TEMPERATURE_UNIT + "</b>";
        forecastTemperature5.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature6 = (TextView) rootView.findViewById(R.id.forecastDay6Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getConverterTemperature(getForecastLowTemperature(weatherForecast, 6))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 6)) + Parameter.TEMPERATURE_UNIT + "</b>";
        forecastTemperature6.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature7 = (TextView) rootView.findViewById(R.id.forecastDay7Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getConverterTemperature(getForecastLowTemperature(weatherForecast, 7))
                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 7)) + Parameter.TEMPERATURE_UNIT + "</b>";
        forecastTemperature7.setText(Html.fromHtml(boldedTemperatureValue));
    }

    private void setDates(View rootView, JSONArray weatherForecast) {
        final String DATE = getResources().getString(R.string.forecast_date);

        TextView forecastDate1 = (TextView) rootView.findViewById(R.id.forecastDay1Date);
        String boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weatherForecast, 1) + ", " + getForecastDate(weatherForecast, 1) + "</b>";
        forecastDate1.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate2 = (TextView) rootView.findViewById(R.id.forecastDay2Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weatherForecast, 2) + ", " + getForecastDate(weatherForecast, 2) + "</b>";
        forecastDate2.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate3 = (TextView) rootView.findViewById(R.id.forecastDay3Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weatherForecast, 3) + ", " + getForecastDate(weatherForecast, 3) + "</b>";
        forecastDate3.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate4 = (TextView) rootView.findViewById(R.id.forecastDay4Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weatherForecast, 4) + ", " + getForecastDate(weatherForecast, 4) + "</b>";
        forecastDate4.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate5 = (TextView) rootView.findViewById(R.id.forecastDay5Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weatherForecast, 5) + ", " + getForecastDate(weatherForecast, 5) + "</b>";
        forecastDate5.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate6 = (TextView) rootView.findViewById(R.id.forecastDay6Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weatherForecast, 6) + ", " + getForecastDate(weatherForecast, 6) + "</b>";
        forecastDate6.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate7 = (TextView) rootView.findViewById(R.id.forecastDay7Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weatherForecast, 7) + ", " + getForecastDate(weatherForecast, 7) + "</b>";
        forecastDate7.setText(Html.fromHtml(boldedDateValue));
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
            return "";
        }
    }

    private String getConverterTemperature(String temperature) {
        if (Parameter.TEMPERATURE_UNIT.equals("°K")) {
            return String.valueOf(UnitConverter.convertCelsiusToKelvin(Double.valueOf(temperature)));
        }
        return temperature;
    }

}
