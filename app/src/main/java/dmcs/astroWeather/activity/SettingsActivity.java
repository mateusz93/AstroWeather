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

import dmcs.astroWeather.R;
import dmcs.astroWeather.util.Parameter;

/**
 * @author Mateusz Wieczorek
 */
public class SettingsActivity extends Activity {

    private EditText latituteValue;
    private EditText longitudeValue;
    private EditText refreshingValue;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        init();
        initOnClicks();
    }

    private void init() {
        latituteValue = (EditText) findViewById(R.id.latituteValue);
        longitudeValue = (EditText) findViewById(R.id.longitudeValue);
        refreshingValue = (EditText) findViewById(R.id.refreshingValue);
        saveButton = (Button) findViewById(R.id.saveButton);
        setDefaultValues();
    }

    private void setDefaultValues() {
        latituteValue.setText(String.valueOf(Parameter.latitute));
        longitudeValue.setText(String.valueOf(Parameter.longitude));
        refreshingValue.setText(String.valueOf(Parameter.refreshIntervalInSec));
    }

    private void initOnClicks() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                try {
                    Parameter.latitute = Double.valueOf(latituteValue.getText().toString());
                    Parameter.longitude = Double.valueOf(longitudeValue.getText().toString());
                    Parameter.refreshIntervalInSec = Integer.valueOf(refreshingValue.getText().toString());
                    vb.vibrate(30);
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    vb.vibrate(300);
                    Toast.makeText(SettingsActivity.this, "Nieprawidłowy format!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}