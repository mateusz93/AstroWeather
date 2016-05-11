package dmcs.astroWeather.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dmcs.astroWeather.Moon;
import dmcs.astroWeather.R;
import dmcs.astroWeather.util.AstroDateTimeFormatter;

/**
 * Created by Mateusz on 2016-05-11.
 */
public class MoonFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private Moon moon;

    public MoonFragment() {
        moon = new Moon();
    }

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

        setTextViews(rootView);

        return rootView;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setTextViews(View rootView) {
        TextView moonAge = (TextView) rootView.findViewById(R.id.moonAge);
        moonAge.setText(getString(R.string.moon_Age) + ": " + String.format("%.4f", moon.getAge()));

        TextView moonIllumination = (TextView) rootView.findViewById(R.id.moonIllumination);
        moonIllumination.setText(getString(R.string.moon_Illumination) + ": " + String.format("%.4f", moon.getIllumination()));

        TextView moonMoonrise = (TextView) rootView.findViewById(R.id.moonMoonrise);
        moonMoonrise.setText(getString(R.string.moon_Moonrise) + ": " + AstroDateTimeFormatter.getFormattedTime(moon.getMoonrise()));

        TextView moonMoonset = (TextView) rootView.findViewById(R.id.moonMoonset);
        moonMoonset.setText(getString(R.string.moon_Moonset) + ": " + AstroDateTimeFormatter.getFormattedTime(moon.getMoonset()));

        TextView moonNextFullMoon = (TextView) rootView.findViewById(R.id.moonNextFullMoon);
        moonNextFullMoon.setText(getString(R.string.moon_NextFullMoon) + ": " + AstroDateTimeFormatter.getFormattedDateAndTime(moon.getNextFullMoon()));

        TextView moonNextNewMoon = (TextView) rootView.findViewById(R.id.moonNextNewMoon);
        moonNextNewMoon.setText(getString(R.string.moon_NextNewMoon) + ": " + AstroDateTimeFormatter.getFormattedDateAndTime(moon.getNextNewMoon()));
    }
}
