package dmcs.astroWeather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dmcs.astroWeather.R;
import dmcs.astroWeather.db.DBLocalization;
import dmcs.astroWeather.db.DBParameter;
import dmcs.astroWeather.db.Localization;
import dmcs.astroWeather.util.Parameter;

/**
 * Created by Mateusz on 2016-05-28.
 */
public class LocalizationsActivity extends Activity {

    private final int DELETE_BUTTON_INTERVAL = 1_000_000;
    private DBLocalization dbLocalization;
    private DBParameter dbParameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localizations);
        dbLocalization = new DBLocalization(this);
        dbParameter = new DBParameter(this);
        generateLocationList();
        initOnClicks();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LocalizationsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void initOnClicks() {
        List<Localization> localizationList = dbLocalization.findAllLocation();
        for (Localization localization : localizationList) {
            Button editButton = (Button) findViewById(Integer.parseInt(localization.getId()));
            Button deleteButton = (Button) findViewById(Integer.parseInt(localization.getId()) + DELETE_BUTTON_INTERVAL);
            final int editButtonId = Integer.parseInt(localization.getId());
            final int deleteButtonId = Integer.parseInt(localization.getId()) + DELETE_BUTTON_INTERVAL;

            setEditButtonsOnClick(editButton, editButtonId);
            setDeleteButtonOnClick(deleteButton, deleteButtonId);
        }

        Button newLocationButton = (Button) findViewById(R.id.newLocation);
        newLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(50);
                Intent intent = new Intent(LocalizationsActivity.this, NewLocalizationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setDeleteButtonOnClick(Button deleteButton, final int deleteButtonId) {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(50);
                List<Localization> localizations = dbLocalization.findAllLocation();
                Localization localization = dbLocalization.findLocationById(deleteButtonId - DELETE_BUTTON_INTERVAL);

                deleteLocalization(localizations, deleteButtonId);
                updateDefaultLocalization(localizations, localization);

                finish();
                startActivity(getIntent());
            }
        });
    }

    private void updateDefaultLocalization(List<Localization> localizations, Localization localization) {
        if (localization.getName().equals(Parameter.LOCALIZATION_NAME)) {
            for (Localization l : localizations) {
                if (!l.getName().equals(Parameter.LOCALIZATION_NAME)) {
                    Parameter.LOCALIZATION_NAME = l.getName();
                    Parameter.LOCALIZATION_LATITUDE = Double.parseDouble(l.getLatitude());
                    Parameter.LOCALIZATION_LONGITUDE = Double.parseDouble(l.getLongitude());

                    dbParameter.updateParameter("LOCALIZATION_NAME", Parameter.LOCALIZATION_NAME);
                    break;
                }
            }
        }
    }

    private void deleteLocalization(List<Localization> localizations, final int deleteButtonId) {
        if (localizations.size() > 1) {
            dbLocalization.deleteLocation(deleteButtonId - DELETE_BUTTON_INTERVAL);
            Toast.makeText(LocalizationsActivity.this, getResources().getString(R.string.localizationDeleted), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LocalizationsActivity.this, getResources().getString(R.string.localizationNotDeleted), Toast.LENGTH_LONG).show();
        }
    }


    private void setEditButtonsOnClick(Button editButton, final int editButtonId) {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(50);
                Intent intent = new Intent(LocalizationsActivity.this, NewLocalizationActivity.class);
                intent.putExtra("id", editButtonId);
                startActivity(intent);
            }
        });
    }

    private void generateLocationList() {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.locationList);

        for (Localization localization : dbLocalization.findAllLocation()) {
            LinearLayout newLinearLayout = getLinearLayout();

            TextView nameView = getNameView(localization);
            Button editButton = getEditButton(localization);
            Button deleteButton = getDeleteButton(localization);

            newLinearLayout.addView(nameView);
            newLinearLayout.addView(editButton);
            newLinearLayout.addView(deleteButton);

            linearLayout.addView(newLinearLayout);
        }
    }

    @NonNull
    private LinearLayout getLinearLayout() {
        LinearLayout newLinearLayout = new LinearLayout(this);
        newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        newLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return newLinearLayout;
    }

    @NonNull
    private TextView getNameView(Localization localization) {
        TextView nameView = new TextView(this);
        nameView.setTextSize(20);
        nameView.setTextColor(Color.WHITE);
        nameView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        nameView.setText(localization.getName());
        return nameView;
    }

    @NonNull
    private Button getDeleteButton(Localization localization) {
        Button deleteButton = new Button(this);
        deleteButton.setText(getResources().getString(R.string.delete));
        deleteButton.setBackgroundColor(Color.WHITE);
        deleteButton.setId(Integer.parseInt(localization.getId()) + DELETE_BUTTON_INTERVAL);
        return deleteButton;
    }

    @NonNull
    private Button getEditButton(Localization localization) {
        Button editButton = new Button(this);
        editButton.setText(getResources().getString(R.string.edit));
        editButton.setId(Integer.parseInt(localization.getId()));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 20, 0);
        editButton.setLayoutParams(params);
        editButton.setBackgroundColor(Color.WHITE);
        return editButton;
    }
}
