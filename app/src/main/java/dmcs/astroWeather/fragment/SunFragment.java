package dmcs.astroWeather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dmcs.astroWeather.R;
import dmcs.astroWeather.Sun;
import dmcs.astroWeather.util.Parameter;

/**
 * @Author Mateusz Wieczorek on 2016-05-11.
 */
public class SunFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private Thread thread;
    private Sun sun;

    public SunFragment() {}

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
        sun = new Sun();
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
                        Thread.sleep(Parameter.REFRESH_INTERVAL_IN_SEC * 1000);
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
        TextView sunAzimuthRise = (TextView) rootView.findViewById(R.id.sunAzimuthRiseValue);
        sunAzimuthRise.setText(String.valueOf(sun.getAzimuthRise()));

        TextView sunAzimuthSet = (TextView) rootView.findViewById(R.id.sunAzimuthSetValue);
        sunAzimuthSet.setText(String.valueOf(sun.getAzimuthSet()));

        TextView sunSunrise = (TextView) rootView.findViewById(R.id.sunSunriseValue);
        sunSunrise.setText(String.valueOf(sun.getSunrise()));

        TextView sunSunset = (TextView) rootView.findViewById(R.id.sunSunsetValue);
        sunSunset.setText(String.valueOf(sun.getSunset()));

        TextView sunTwilightMorning = (TextView) rootView.findViewById(R.id.sunTwilightMorningValue);
        sunTwilightMorning.setText(String.valueOf(sun.getTwilightMorning()));

        TextView sunTwilightEvening = (TextView) rootView.findViewById(R.id.sunTwilightEveningValue);
        sunTwilightEvening.setText(String.valueOf(sun.getTwilightEvening()));
    }
}
