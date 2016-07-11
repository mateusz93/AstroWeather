package dmcs.astroWeather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dmcs.astroWeather.Moon;
import dmcs.astroWeather.R;
import dmcs.astroWeather.util.AstroDateTimeFormatter;
import dmcs.astroWeather.util.Parameter;
import dmcs.astroWeather.util.StringFormatter;

/**
 * @Author Mateusz Wieczorek on 2016-05-11.
 */
public class MoonFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private Thread thread;
    private Moon moon;

    public MoonFragment() {}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MoonFragment newInstance(int sectionNumber) {
        MoonFragment fragment = new MoonFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moon_fragment, container, false);
        moon = new Moon();

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
        TextView moonAge = (TextView) rootView.findViewById(R.id.moonAgeValue);
        moonAge.setText(String.valueOf(moon.getAge()));

        TextView moonIllumination = (TextView) rootView.findViewById(R.id.moonIlluminationValue);
        moonIllumination.setText(String.valueOf(moon.getIllumination() * 100) + "%");

        TextView moonMoonrise = (TextView) rootView.findViewById(R.id.moonMoonriseValue);
        moonMoonrise.setText(String.valueOf(moon.getMoonrise()));

        TextView moonMoonset = (TextView) rootView.findViewById(R.id.moonMoonsetValue);
        moonMoonset.setText(String.valueOf(moon.getMoonset()));

        TextView moonNextFullMoon = (TextView) rootView.findViewById(R.id.moonNextFullMoonValue);
        moonNextFullMoon.setText(String.valueOf(moon.getNextFullMoon()));

        TextView moonNextNewMoon = (TextView) rootView.findViewById(R.id.moonNextNewMoonValue);
        moonNextNewMoon.setText(String.valueOf(moon.getNextNewMoon()));
    }
}
