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
import org.json.JSONObject;

import java.io.IOException;

import dmcs.astroWeather.R;
import dmcs.astroWeather.util.Parameter;
import dmcs.astroWeather.util.StringFormatter;
import dmcs.astroWeather.util.Unit;
import dmcs.astroWeather.util.WeatherDownloader;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class ForecastWeatherFragment extends Fragment {

    private Thread thread;

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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void setTextViews(final View rootView) {
        try {
            JSONObject weather = WeatherDownloader.getWeatherByLatitudeAndLongitude(String.valueOf(Parameter.latitute), String.valueOf(Parameter.longitude));

            setIcons(rootView, weather);
            setDates(rootView, weather);
            setTemperatures(rootView, weather);
            setDescriptions(rootView, weather);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDescriptions(View rootView, JSONObject weather) {
        final String DESCRIPTION = getResources().getString(R.string.forecast_description);

        TextView forecastDescription1 = (TextView) rootView.findViewById(R.id.forecastDay1Description);
        String boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weather, 1) + "</b>";
        forecastDescription1.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription2 = (TextView) rootView.findViewById(R.id.forecastDay2Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weather, 2) + "</b>";
        forecastDescription2.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription3 = (TextView) rootView.findViewById(R.id.forecastDay3Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weather, 3) + "</b>";
        forecastDescription3.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription4 = (TextView) rootView.findViewById(R.id.forecastDay4Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weather, 4) + "</b>";
        forecastDescription4.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription5 = (TextView) rootView.findViewById(R.id.forecastDay5Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weather, 5) + "</b>";
        forecastDescription5.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription6 = (TextView) rootView.findViewById(R.id.forecastDay6Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weather, 6) + "</b>";
        forecastDescription6.setText(Html.fromHtml(boldedDescriptionValue));

        TextView forecastDescription7 = (TextView) rootView.findViewById(R.id.forecastDay7Description);
        boldedDescriptionValue = DESCRIPTION + ": " + "<b>" + getForecastDescription(weather, 7) + "</b>";
        forecastDescription7.setText(Html.fromHtml(boldedDescriptionValue));
    }

    private void setTemperatures(View rootView, JSONObject weather) {
        final String TEMPERATURE = getResources().getString(R.string.forecast_temperature);

        TextView forecastTemperature1 = (TextView) rootView.findViewById(R.id.forecastDay1Temperature);
        String boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getForecastLowTemperature(weather, 1) + " - "
                + getForecastHighTemperature(weather, 1) + Unit.TEMPERATURE_UNIT + "</b>";
        forecastTemperature1.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature2 = (TextView) rootView.findViewById(R.id.forecastDay2Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getForecastLowTemperature(weather, 2) + " - "
                + getForecastHighTemperature(weather, 2) + Unit.TEMPERATURE_UNIT + "</b>";
        forecastTemperature2.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature3 = (TextView) rootView.findViewById(R.id.forecastDay3Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getForecastLowTemperature(weather, 3) + " - "
                + getForecastHighTemperature(weather, 3) + Unit.TEMPERATURE_UNIT + "</b>";
        forecastTemperature3.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature4 = (TextView) rootView.findViewById(R.id.forecastDay4Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getForecastLowTemperature(weather, 4) + " - "
                + getForecastHighTemperature(weather, 4) + Unit.TEMPERATURE_UNIT + "</b>";
        forecastTemperature4.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature5 = (TextView) rootView.findViewById(R.id.forecastDay5Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getForecastLowTemperature(weather, 5) + " - "
                + getForecastHighTemperature(weather, 5) + Unit.TEMPERATURE_UNIT + "</b>";
        forecastTemperature5.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature6 = (TextView) rootView.findViewById(R.id.forecastDay6Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getForecastLowTemperature(weather, 6) + " - "
                + getForecastHighTemperature(weather, 6) + Unit.TEMPERATURE_UNIT + "</b>";
        forecastTemperature6.setText(Html.fromHtml(boldedTemperatureValue));

        TextView forecastTemperature7 = (TextView) rootView.findViewById(R.id.forecastDay7Temperature);
        boldedTemperatureValue = TEMPERATURE + ": " + "<b>" + getForecastLowTemperature(weather, 7) + " - "
                + getForecastHighTemperature(weather, 7) + Unit.TEMPERATURE_UNIT + "</b>";
        forecastTemperature7.setText(Html.fromHtml(boldedTemperatureValue));
    }

    private void setDates(View rootView, JSONObject weather) {
        final String DATE = getResources().getString(R.string.forecast_date);

        TextView forecastDate1 = (TextView) rootView.findViewById(R.id.forecastDay1Date);
        String boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weather, 1) + ", " + getForecastDate(weather, 1) + "</b>";
        forecastDate1.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate2 = (TextView) rootView.findViewById(R.id.forecastDay2Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weather, 2) + ", " + getForecastDate(weather, 2) + "</b>";
        forecastDate2.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate3 = (TextView) rootView.findViewById(R.id.forecastDay3Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weather, 3) + ", " + getForecastDate(weather, 3) + "</b>";
        forecastDate3.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate4 = (TextView) rootView.findViewById(R.id.forecastDay4Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weather, 4) + ", " + getForecastDate(weather, 4) + "</b>";
        forecastDate4.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate5 = (TextView) rootView.findViewById(R.id.forecastDay5Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weather, 5) + ", " + getForecastDate(weather, 5) + "</b>";
        forecastDate5.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate6 = (TextView) rootView.findViewById(R.id.forecastDay6Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weather, 6) + ", " + getForecastDate(weather, 6) + "</b>";
        forecastDate6.setText(Html.fromHtml(boldedDateValue));

        TextView forecastDate7 = (TextView) rootView.findViewById(R.id.forecastDay7Date);
        boldedDateValue = DATE + ": " + "<b>" + getForecastDay(weather, 7) + ", " + getForecastDate(weather, 7) + "</b>";
        forecastDate7.setText(Html.fromHtml(boldedDateValue));
    }

    private void setIcons(View rootView, JSONObject weather) {
        ImageView forecastIcon1 = (ImageView) rootView.findViewById(R.id.forecastDay1Icon);
        String iconNumber = getForecastIconNumber(weather, 1);
        int id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon1.setImageResource(id);

        ImageView forecastIcon2 = (ImageView) rootView.findViewById(R.id.forecastDay2Icon);
        iconNumber = getForecastIconNumber(weather, 2);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon2.setImageResource(id);

        ImageView forecastIcon3 = (ImageView) rootView.findViewById(R.id.forecastDay3Icon);
        iconNumber = getForecastIconNumber(weather, 3);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon3.setImageResource(id);

        ImageView forecastIcon4 = (ImageView) rootView.findViewById(R.id.forecastDay4Icon);
        iconNumber = getForecastIconNumber(weather, 4);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon4.setImageResource(id);

        ImageView forecastIcon5 = (ImageView) rootView.findViewById(R.id.forecastDay5Icon);
        iconNumber = getForecastIconNumber(weather, 5);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon5.setImageResource(id);

        ImageView forecastIcon6 = (ImageView) rootView.findViewById(R.id.forecastDay6Icon);
        iconNumber = getForecastIconNumber(weather, 6);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon6.setImageResource(id);

        ImageView forecastIcon7 = (ImageView) rootView.findViewById(R.id.forecastDay7Icon);
        iconNumber = getForecastIconNumber(weather, 7);
        id = getResources().getIdentifier("icon_" + iconNumber, "drawable", getContext().getPackageName());
        forecastIcon7.setImageResource(id);
    }

    private String getForecastIconNumber(JSONObject weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "code");
    }

    private String getForecastDate(JSONObject weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "date");
    }

    private String getForecastDay(JSONObject weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "day");
    }

    private String getForecastHighTemperature(JSONObject weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "high");
    }

    private String getForecastLowTemperature(JSONObject weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "low");
    }

    private String getForecastDescription(JSONObject weather, int dayNumber) {
        return getForecastItem(weather, dayNumber, "text");
    }

    private String getForecastItem(JSONObject weather, int dayNumber, String item) {
        try {
            JSONArray jsonArray = weather.getJSONObject("item").getJSONArray("forecast");
            String iconNumber = jsonArray.getJSONObject(dayNumber).getString(item);
            return iconNumber;
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
