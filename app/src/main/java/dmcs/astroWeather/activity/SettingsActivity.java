package dmcs.astroWeather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dmcs.astroWeather.R;
import dmcs.astroWeather.db.DBLocalization;
import dmcs.astroWeather.db.DBParameter;
import dmcs.astroWeather.db.Localization;
import dmcs.astroWeather.util.Parameter;

/**
 * @author Mateusz Wieczorek
 */
public class SettingsActivity extends Activity {

    private DBLocalization db;
    private TextView city;
    private EditText refreshingValue;
    private Button saveButton;
    private Button selectButton;
    private RadioButton kilometerRadioButton;
    private RadioButton mileRadioButton;
    private RadioButton celsiusRadioButton;
    private RadioButton kelvinRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        db = new DBLocalization(this);
        init();
        initOnClicks();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void init() {
        city = (TextView) findViewById(R.id.settingsCity);
        refreshingValue = (EditText) findViewById(R.id.refreshingValue);
        saveButton = (Button) findViewById(R.id.saveButton);
        selectButton = (Button) findViewById(R.id.selectCity);
        setDefaultValues();
    }

    private void setDefaultValues() {
        celsiusRadioButton = (RadioButton) findViewById(R.id.celsiusRadioButton);
        kelvinRadioButton = (RadioButton) findViewById(R.id.kelvinRadioButton);
        kilometerRadioButton = (RadioButton) findViewById(R.id.kilometerRadioButton);
        mileRadioButton = (RadioButton) findViewById(R.id.mileRadioButton);

        setTemperatureUnits();
        setSpeedUnit();

        city.setText(getResources().getString(R.string.settings_city) + ": " + String.valueOf(Parameter.LOCALIZATION_NAME));
        refreshingValue.setText(String.valueOf(Parameter.REFRESH_INTERVAL_IN_SEC));
    }

    private void setSpeedUnit() {
        if (Parameter.SPEED_UNIT.equals("km/h")) {
            kilometerRadioButton.setChecked(true);
            mileRadioButton.setChecked(false);
        } else {
            kilometerRadioButton.setChecked(false);
            mileRadioButton.setChecked(true);
        }
    }

    private void setTemperatureUnits() {
        if (Parameter.TEMPERATURE_UNIT.equals("°C")) {
            celsiusRadioButton.setChecked(true);
            kelvinRadioButton.setChecked(false);
        } else {
            celsiusRadioButton.setChecked(false);
            kelvinRadioButton.setChecked(true);
        }
    }

    private void initOnClicks() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                try {
                    Parameter.REFRESH_INTERVAL_IN_SEC = Integer.valueOf(refreshingValue.getText().toString());
                    saveParametersToDatabase();
                    vb.vibrate(30);
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    vb.vibrate(300);
                    Toast.makeText(SettingsActivity.this, "Nieprawidłowy format!", Toast.LENGTH_LONG).show();
                }
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(30);
                Intent intent = new Intent(SettingsActivity.this, LocalizationListActivity.class);
                startActivity(intent);
            }
        });

        kilometerRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(30);
            }
        });

        mileRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(30);
            }
        });

        celsiusRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(30);
            }
        });

        kelvinRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(30);
            }
        });
    }

    private void saveParametersToDatabase() {
        DBParameter dbParameter = new DBParameter(this);
        if (kilometerRadioButton.isChecked()) {
            Parameter.SPEED_UNIT = "km/h";
        } else {
            Parameter.SPEED_UNIT = "mi/h";
        }
        if (celsiusRadioButton.isChecked()) {
            Parameter.TEMPERATURE_UNIT = "°C";
        } else {
            Parameter.TEMPERATURE_UNIT = "°K";
        }
        dbParameter.updateParameter("LOCALIZATION_NAME", Parameter.LOCALIZATION_NAME);
        dbParameter.updateParameter("PRESSURE_UNIT", Parameter.PRESSURE_UNIT);
        dbParameter.updateParameter("REFRESH_INTERVAL_IN_SEC", String.valueOf(Parameter.REFRESH_INTERVAL_IN_SEC));
        dbParameter.updateParameter("TEMPERATURE_UNIT", Parameter.TEMPERATURE_UNIT);
        dbParameter.updateParameter("SPEED_UNIT", Parameter.SPEED_UNIT);
    }

}
