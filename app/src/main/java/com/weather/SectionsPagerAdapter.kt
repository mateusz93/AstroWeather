package com.weather

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.weather.fragment.ForecastWeatherFragment
import com.weather.fragment.WeatherFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return WeatherFragment.newInstance()
            1 -> return ForecastWeatherFragment.newInstance()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Weather"
            1 -> return "Forecast"
        }
        return null
    }
}