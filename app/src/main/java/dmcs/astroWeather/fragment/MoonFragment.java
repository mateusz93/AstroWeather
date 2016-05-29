package dmcs.astroWeather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import dmcs.astroWeather.Moon;
import dmcs.astroWeather.R;
import dmcs.astroWeather.util.AstroDateTimeFormatter;
import dmcs.astroWeather.util.Parameter;
import dmcs.astroWeather.util.StringFormatter;

/**
 * Created by Mateusz on 2016-05-11.
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
        moon = new Moon();

        TextView moonAge = (TextView) rootView.findViewById(R.id.moonAge);
        moonAge.setText(StringFormatter.padRight(getString(R.string.moon_Age) + ": ", 30)
                + String.format("%10s", String.format("%.4f", moon.getAge())));

        TextView moonIllumination = (TextView) rootView.findViewById(R.id.moonIllumination);
        moonIllumination.setText(StringFormatter.padRight(getString(R.string.moon_Illumination) + ": ", 30)
                + String.format("%10s", String.format("%.4f", moon.getIllumination())));

        TextView moonMoonrise = (TextView) rootView.findViewById(R.id.moonMoonrise);
        moonMoonrise.setText(StringFormatter.padRight(getString(R.string.moon_Moonrise) + ": ", 30)
                + String.format("%10s", AstroDateTimeFormatter.getFormattedTime(moon.getMoonrise())));

        TextView moonMoonset = (TextView) rootView.findViewById(R.id.moonMoonset);
        moonMoonset.setText(StringFormatter.padRight(getString(R.string.moon_Moonset) + ": ", 30)
                + String.format("%10s", AstroDateTimeFormatter.getFormattedTime(moon.getMoonset())));

        TextView moonNextFullMoon = (TextView) rootView.findViewById(R.id.moonNextFullMoon);
        moonNextFullMoon.setText(StringFormatter.padRight(getString(R.string.moon_NextFullMoon) + ": ", 20)
                + String.format("%20s", AstroDateTimeFormatter.getFormattedDateAndTime(moon.getNextFullMoon())));

        TextView moonNextNewMoon = (TextView) rootView.findViewById(R.id.moonNextNewMoon);
        moonNextNewMoon.setText(StringFormatter.padRight(getString(R.string.moon_NextNewMoon) + ": ", 20)
                + String.format("%20s", AstroDateTimeFormatter.getFormattedDateAndTime(moon.getNextNewMoon())));
    }
}
