package dmcs.astroWeather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import dmcs.astroWeather.R;

/**
 * Created by Mateusz on 2016-06-01.
 */
public class NewLocalizationActivity extends Activity {

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_localization);
        initOnClicks();
    }

    private void initOnClicks() {
        saveButton = (Button) findViewById(R.id.addNewLocalizationButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                Intent intent = new Intent(NewLocalizationActivity.this, LocalizationsActivity.class);
                startActivity(intent);
            }
        });
    }
}
