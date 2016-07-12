package dmcs.astroWeather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import dmcs.astroWeather.R;
import dmcs.astroWeather.db.DBLocalization;
import dmcs.astroWeather.db.Localization;
import dmcs.astroWeather.task.DownloadWoeidByCityAndCountryTask;
import dmcs.astroWeather.task.DownloadWoeidByLatitudeAndLongitudeTask;
import dmcs.astroWeather.util.WeatherDownloader;

/**
 * @Author Mateusz Wieczorek on 2016-06-01.
 */
public class NewLocalizationActivity extends Activity {

    private DBLocalization database;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_localization);
        database = new DBLocalization(this);
        extras = getIntent().getExtras();
        if (extras != null) {
            setFields(extras);
        }
        initOnClicks();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewLocalizationActivity.this, LocalizationsActivity.class);
        startActivity(intent);
    }

    private void setFields(Bundle extras) {
        int localizationId = (int) extras.get("id");
        EditText name = (EditText) findViewById(R.id.LocalizationNameValue);
        EditText country = (EditText) findViewById(R.id.LocalizationCountryValue);
        EditText city = (EditText) findViewById(R.id.LocalizationCityValue);
        EditText latitude = (EditText) findViewById(R.id.LocalizationLatituteValue);
        EditText longitude = (EditText) findViewById(R.id.LocalizationLongitudeValue);
        Localization localization = database.findLocationById(localizationId);
        name.setText(localization.getName());
        name.setEnabled(false);
        country.setText(localization.getCountry());
        city.setText(localization.getCity());
        latitude.setText(localization.getLatitude());
        longitude.setText(localization.getLongitude());
    }

    private void initOnClicks() {
        Button saveButton = (Button) findViewById(R.id.addNewLocalizationButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.LocalizationNameValue)).getText().toString();
                String country = ((EditText) findViewById(R.id.LocalizationCountryValue)).getText().toString();
                String city = ((EditText) findViewById(R.id.LocalizationCityValue)).getText().toString();
                String latitude = ((EditText) findViewById(R.id.LocalizationLatituteValue)).getText().toString();
                String longitude = ((EditText) findViewById(R.id.LocalizationLongitudeValue)).getText().toString();

                downloadWeather(name, city, country, latitude, longitude);
            }
        });
    }

    private void downloadWeather(String name, String city, String country, String latitude, String longitude) {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        try {
            String woeid = new DownloadWoeidByCityAndCountryTask().execute(city, country).get();
            if (woeid == null) {
                woeid = new DownloadWoeidByLatitudeAndLongitudeTask().execute(latitude, longitude).get();
                if (woeid == null) {
                    vb.vibrate(300);
                    Toast.makeText(NewLocalizationActivity.this, getResources().getString(R.string.incorrectLocalization), Toast.LENGTH_LONG).show();
                    return;
                }
            }
            saveLocation(name, woeid);

            Intent intent = new Intent(NewLocalizationActivity.this, LocalizationsActivity.class);
            vb.vibrate(50);
            Toast.makeText(NewLocalizationActivity.this, getResources().getString(R.string.localizationSaved), Toast.LENGTH_LONG).show();
            startActivity(intent);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void saveLocation(String name, String woeid) {
        try {
            JSONObject jsonWeather = WeatherDownloader.getWeatherByWoeid(woeid);

            String latitute = jsonWeather.getJSONObject("item").getString("lat");
            String longitude = jsonWeather.getJSONObject("item").getString("long");
            String city = jsonWeather.getJSONObject("location").getString("city");
            String country = jsonWeather.getJSONObject("location").getString("country");
            String weather = jsonWeather.toString();
            String weatherForecast = jsonWeather.getJSONObject("item").getJSONArray("forecast").toString();
            String lastUpdate = String.valueOf(new Date().getTime());

            Localization localization = new Localization();
            localization.setName(name);
            localization.setWoeid(woeid);
            localization.setLatitude(latitute);
            localization.setLongitude(longitude);
            localization.setCity(city);
            localization.setCountry(country);
            localization.setWeather(weather);
            localization.setForecast(weatherForecast);
            localization.setLastUpdate(lastUpdate);

            if (extras != null) {
                String localizationId = database.findLocationByName(name).getId();
                localization.setId(localizationId);
                database.updateLocation(localization);
            } else {
                database.insertLocation(localization);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
