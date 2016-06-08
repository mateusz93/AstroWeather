package dmcs.astroWeather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dmcs.astroWeather.fragment.ForecastWeatherFragment;
import dmcs.astroWeather.fragment.MoonFragment;
import dmcs.astroWeather.fragment.SunFragment;
import dmcs.astroWeather.fragment.WeatherFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SunFragment.newInstance(0);
            case 1:
                return MoonFragment.newInstance(1);
            case 2:
                return WeatherFragment.newInstance(2);
            case 3:
                return ForecastWeatherFragment.newInstance(3);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sun";
            case 1:
                return "Moon";
            case 2:
                return "Weather";
            case 3:
                return "Forecast";
        }
        return null;
    }
}