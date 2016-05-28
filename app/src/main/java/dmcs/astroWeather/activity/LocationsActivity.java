package dmcs.astroWeather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dmcs.astroWeather.R;
import dmcs.astroWeather.db.DBHelper;
import dmcs.astroWeather.db.Location;

/**
 * Created by Mateusz on 2016-05-28.
 */
public class LocationsActivity extends Activity {

    private DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localizations);
        database = new DBHelper(this);
        generateLocationList();
       // generateExpandableLocationList();
    }

    private void generateExpandableLocationList() {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.locationList);

        for (Location location : database.findAllLocation()) {
            ExpandableListView expandableListView = new ExpandableListView(this);

            LinearLayout newLinearLayout = new LinearLayout(this);
            newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            Button editButton = new Button(this);
            Button deleteButton = new Button(this);
            TextView nameView = new TextView(this);
            nameView.setTextSize(20);
            nameView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            editButton.setText(getResources().getString(R.string.edit));
            deleteButton.setText(getResources().getString(R.string.delete));
            nameView.setText(location.getName());
            newLinearLayout.addView(nameView);
            newLinearLayout.addView(editButton);
            newLinearLayout.addView(deleteButton);

            linearLayout.addView(newLinearLayout);
        }
    }

    private void generateLocationList() {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.locationList);

        for (Location location : database.findAllLocation()) {
            LinearLayout newLinearLayout = new LinearLayout(this);
            newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            Button editButton = new Button(this);
            Button deleteButton = new Button(this);
            TextView nameView = new TextView(this);
            nameView.setTextSize(20);
            nameView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            editButton.setText(getResources().getString(R.string.edit));
            deleteButton.setText(getResources().getString(R.string.delete));
            nameView.setText(location.getName());
            newLinearLayout.addView(nameView);
            newLinearLayout.addView(editButton);
            newLinearLayout.addView(deleteButton);

            linearLayout.addView(newLinearLayout);
        }
    }
}
