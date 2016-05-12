package dmcs.astroWeather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import dmcs.astroWeather.R;
import dmcs.astroWeather.SectionsPagerAdapter;
import dmcs.astroWeather.Sun;
import dmcs.astroWeather.util.AstroDateTimeFormatter;
import dmcs.astroWeather.util.Parameter;

/**
 * Created by Mateusz on 2016-05-11.
 */
public class SunFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private Thread thread;
    private Sun sun;

    public SunFragment() {
        sun = new Sun();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SunFragment newInstance(int sectionNumber) {
        SunFragment fragment = new SunFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sun_fragment, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        thread = createThread(rootView);
        thread.start();

        return rootView;
    }

    @NonNull
    private Thread createThread(final View rootView) {
        return new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setTextViews(rootView);
                            }
                        });
                        Thread.sleep(Parameter.refreshIntervalInSec * 1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setTextViews(View rootView) {
        TextView sunAzimuthRise = (TextView) rootView.findViewById(R.id.sunAzimuthRise);
        sunAzimuthRise.setText(getString(R.string.sun_AzimuthRise) + ": " + String.format("%.4f", sun.getAzimuthRise()));

        TextView sunAzimuthSet = (TextView) rootView.findViewById(R.id.sunAzimuthSet);
        sunAzimuthSet.setText(getString(R.string.sun_AzimuthSet) + ": " + String.format("%.4f", sun.getAzimuthSet()));

        TextView sunSunrise = (TextView) rootView.findViewById(R.id.sunSunrise);
        sunSunrise.setText(getString(R.string.sun_Sunrise) + ": " + AstroDateTimeFormatter.getFormattedTime(sun.getSunrise()));

        TextView sunSunset = (TextView) rootView.findViewById(R.id.sunSunset);
        sunSunset.setText(getString(R.string.sun_Sunset) + ": " + AstroDateTimeFormatter.getFormattedTime(sun.getSunset()));

        TextView sunTwilightMorning = (TextView) rootView.findViewById(R.id.sunTwilightMorning);
        sunTwilightMorning.setText(getString(R.string.sun_TwilightMorning) + ": " + AstroDateTimeFormatter.getFormattedTime(sun.getTwilightMorning()));

        TextView sunTwilightEvening = (TextView) rootView.findViewById(R.id.sunTwilightEvening);
        sunTwilightEvening.setText(getString(R.string.sun_TwilightEvening) + ": " + AstroDateTimeFormatter.getFormattedTime(sun.getTwilightEvening()));

        TextView time = (TextView) rootView.findViewById(dmcs.astroWeather.R.id.helpTime);
        if (time != null) {
            time.setText(DateFormat.getDateTimeInstance().format(new Date()));
        }
    }

}
