package dmcs.astroWeather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import dmcs.astroWeather.R;
import dmcs.astroWeather.SectionsPagerAdapter;
import dmcs.astroWeather.db.DBLocalization;
import dmcs.astroWeather.db.DBParameter;
import dmcs.astroWeather.db.Localization;
import dmcs.astroWeather.util.Parameter;

public class MainActivity extends AppCompatActivity {

    private Thread timeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateParametersFromDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        TextView coordinates = (TextView) findViewById(R.id.coordinates);
        if (coordinates != null) {
            coordinates.setText(getString(R.string.coordinates) + " " + Parameter.LOCALIZATION_LATITUDE + ", " + Parameter.LOCALIZATION_LONGITUDE);
        }

        timeThread = createTimeThread();
        timeThread.start();

        if (viewPager != null) {
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(sectionsPagerAdapter);
            if (tabLayout != null) {
                tabLayout.setupWithViewPager(viewPager);
            }
        } else {

        }

        Toast.makeText(MainActivity.this, getResources().getString(R.string.internetAccess), Toast.LENGTH_LONG).show();
    }

    @NonNull
    private Thread createTimeThread() {
        return new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView time = (TextView) findViewById(R.id.time);
                                if (time != null) {
                                    time.setText(DateFormat.getDateTimeInstance().format(new Date()));
                                }
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(30);
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_localizations) {
            Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(30);
            Intent intent = new Intent(MainActivity.this, LocalizationsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveParametersToDatabase();
        timeThread.interrupt();
        getDelegate().onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveParametersToDatabase();
    }

    private void saveParametersToDatabase() {
        DBParameter dbParameter = new DBParameter(this);
        dbParameter.updateParameter("LOCALIZATION_NAME", Parameter.LOCALIZATION_NAME);
        dbParameter.updateParameter("PRESSURE_UNIT", Parameter.PRESSURE_UNIT);
        dbParameter.updateParameter("REFRESH_INTERVAL_IN_SEC", String.valueOf(Parameter.REFRESH_INTERVAL_IN_SEC));
        dbParameter.updateParameter("TEMPERATURE_UNIT", Parameter.TEMPERATURE_UNIT);
        dbParameter.updateParameter("SPEED_UNIT", Parameter.SPEED_UNIT);
    }

    private void updateParametersFromDatabase() {
        DBParameter dbParameter = new DBParameter(this);
        DBLocalization dbLocalization = new DBLocalization(this);
        Parameter.LOCALIZATION_NAME = dbParameter.findParamValueByParamName("LOCALIZATION_NAME");
        Localization localization = dbLocalization.findLocationByName(Parameter.LOCALIZATION_NAME);
        Parameter.LOCALIZATION_LATITUDE = Double.valueOf(localization.getLatitude());
        Parameter.LOCALIZATION_LONGITUDE = Double.valueOf(localization.getLongitude());
        Parameter.PRESSURE_UNIT = dbParameter.findParamValueByParamName("PRESSURE_UNIT");
        Parameter.REFRESH_INTERVAL_IN_SEC = Integer.valueOf(dbParameter.findParamValueByParamName("REFRESH_INTERVAL_IN_SEC"));
        Parameter.TEMPERATURE_UNIT = dbParameter.findParamValueByParamName("TEMPERATURE_UNIT");
        Parameter.SPEED_UNIT = dbParameter.findParamValueByParamName("SPEED_UNIT");
    }
}
