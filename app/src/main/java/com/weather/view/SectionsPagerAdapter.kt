package com.weather.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.weather.fragment.ForecastWeatherFragment
import com.weather.fragment.WeatherFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return WeatherFragment.newInstance()
            1 -> return ForecastWeatherFragment.newInstance()
        }
        return null
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Pogoda"
            1 -> return "Prognoza"
        }
        return ""
    }
}