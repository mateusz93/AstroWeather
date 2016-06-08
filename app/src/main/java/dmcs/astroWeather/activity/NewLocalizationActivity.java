package dmcs.astroWeather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dmcs.astroWeather.R;
import dmcs.astroWeather.db.DBHelper;
import dmcs.astroWeather.db.Localization;
import dmcs.astroWeather.util.WeatherDownloader;

/**
 * Created by Mateusz on 2016-06-01.
 */
public class NewLocalizationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_localization);
        initOnClicks();
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

                new Connection(name, city, country, latitude, longitude).execute();
            }
        });
    }

    private class Connection extends AsyncTask {
        private String name;
        private String city;
        private String country;
        private String latitude;
        private String longitude;

        public Connection(String name, String city, String country, String latitude, String longitude) {
            this.name = name;
            this.city = city;
            this.country = country;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            connect(name, city, country, latitude, longitude);
            return null;
        }

    }

    private void connect(String name, String city, String country, String latitude, String longitude) {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        try {
            if (isCorrectCityAndCountry(city, country)) {
                String woeid = WeatherDownloader.getWoeidByCityAndCountry(city, country);
                saveLocation(name, woeid);

                Intent intent = new Intent(NewLocalizationActivity.this, LocalizationsActivity.class);
                vb.vibrate(30);
//                Toast.makeText(NewLocalizationActivity.this, getResources().getString(R.string.localizationSaved), Toast.LENGTH_LONG).show();
                startActivity(intent);

            } else if (isCorrectLatitudeAndLongitude(latitude, longitude)){
                String woeid = WeatherDownloader.getWoeidByLatitudeAndLongitude(latitude, longitude);
                saveLocation(name, woeid);

                Intent intent = new Intent(NewLocalizationActivity.this, LocalizationsActivity.class);
                startActivity(intent);
                vb.vibrate(30);
  //              Toast.makeText(NewLocalizationActivity.this, getResources().getString(R.string.localizationSaved), Toast.LENGTH_LONG).show();
            } else {
                vb.vibrate(500);
    //            Toast.makeText(NewLocalizationActivity.this, getResources().getString(R.string.incorrectLocalization), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCorrectCityAndCountry(String city, String country) {
        try {
            WeatherDownloader.getWoeidByCityAndCountry(city, country);
            return true;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isCorrectLatitudeAndLongitude(String lalitude, String longitude) {
        try {
            WeatherDownloader.getWeatherByLatitudeAndLongitude(lalitude, longitude);
            return true;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveLocation(String name, String woeid) {
        try {
            JSONObject jsonWeather = WeatherDownloader.getWeatherByWoeid(woeid);

            String latitute = jsonWeather.getJSONObject("item").getString("lat");
            String longitude = jsonWeather.getJSONObject("item").getString("long");
            String city = jsonWeather.getJSONObject("location").getString("city");
            String country = jsonWeather.getJSONObject("location").getString("country");

            Localization localization = new Localization();
            localization.setName(name);
            localization.setWoeid(woeid);
            localization.setLatitude(latitute);
            localization.setLongitude(longitude);
            localization.setCity(city);
            localization.setCountry(country);

            DBHelper db = new DBHelper(this);
            db.insertLocation(localization);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
