package dmcs.astroWeather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import dmcs.astroWeather.R;
import dmcs.astroWeather.db.DBLocalization;
import dmcs.astroWeather.db.Localization;
import dmcs.astroWeather.util.Parameter;

/**
 * Created by Mateusz on 2016-06-11.
 */
public class LocalizationListActivity extends Activity {

    private RadioGroup citiesList;
    private DBLocalization db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localization_list);
        db = new DBLocalization(this);
        init();
    }

    private void init() {
        citiesList = (RadioGroup) findViewById(R.id.citiesRadioGroup);

        citiesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(30);
                Intent intent = new Intent(LocalizationListActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        List<Localization> localizations = db.findAllLocation();

        for (Localization l : localizations) {
            RadioButton city = new RadioButton(this);
            city.setText(l.getName());
            city.setTextColor(Color.WHITE);
            citiesList.addView(city);
        }
    }

    @Override
    public void onBackPressed() {
        int radioButtonID = citiesList.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) citiesList.findViewById(radioButtonID);
        if (radioButton != null) {
            Parameter.LOCALIZATION_NAME = String.valueOf(radioButton.getText());
        }
        Intent intent = new Intent(LocalizationListActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
