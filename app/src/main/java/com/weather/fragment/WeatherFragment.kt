package com.weather.fragment

import android.os.Bundle


import android.os.StrictMode
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.weather.R
import com.weather.util.DefaultParameter
import com.weather.net.WeatherDownloader
import org.json.JSONObject

/**
 * @author Mateusz Wieczorek
 */
class WeatherFragment : Fragment() {

    companion object {
        fun newInstance(): WeatherFragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.weather_fragment, container, false)
        activity.runOnUiThread { setValues(rootView) }
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private fun setValues(rootView: View) {
        try {
            val weather = downloadWeather()
            val city = weather.getJSONObject("location").getString("city")
            val latitude = DefaultParameter.LOCALIZATION_LATITUDE.toString()
            val longitude = DefaultParameter.LOCALIZATION_LONGITUDE.toString()
            val temperature = weather.getJSONObject("item").getJSONObject("condition").getString("temp")
            val windDirection = weather.getJSONObject("wind").getString("direction")
            val windSpeed = weather.getJSONObject("wind").getString("speed")
            val atmosphereHumidity = weather.getJSONObject("atmosphere").getString("humidity")
            val atmosphereVisibility = weather.getJSONObject("atmosphere").getString("visibility")
            val atmospherePressure = weather.getJSONObject("atmosphere").getString("pressure")
            val description = weather.getJSONObject("item").getString("description")
            val iconNumber = getIconNumberFromDescription(description)
            val condition = getCurrentConditionFromDescription(description)
            val formattedTime = getFormattedTime(weather.getString("lastBuildDate"))
            val imageResourceId = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)

            rootView.findViewById<ImageView>(R.id.weatherIcon).setImageResource(imageResourceId)
            rootView.findViewById<TextView>(R.id.weatherCityValue).text = city
            rootView.findViewById<TextView>(R.id.weatherLatitudeValue).text = latitude
            rootView.findViewById<TextView>(R.id.weatherLongitudeValue).text = longitude
            rootView.findViewById<TextView>(R.id.weatherTemperatureValue).text = temperature + DefaultParameter.TEMPERATURE_UNIT
            rootView.findViewById<TextView>(R.id.weatherLocalTimeValue).text = formattedTime
            rootView.findViewById<TextView>(R.id.weatherWindDirectionValue).text = windDirection
            rootView.findViewById<TextView>(R.id.weatherWindSpeedValue).text = windSpeed + DefaultParameter.SPEED_UNIT
            rootView.findViewById<TextView>(R.id.weatherAtmosphereHumidityValue).text = atmosphereHumidity + DefaultParameter.HUMIDITY_UNIT
            rootView.findViewById<TextView>(R.id.weatherAtmosphereVisibilityValue).text = atmosphereVisibility
            rootView.findViewById<TextView>(R.id.weatherAtmospherePressureValue).text = atmospherePressure + DefaultParameter.PRESSURE_UNIT
            rootView.findViewById<TextView>(R.id.weatherDescriptionValue).text = condition
        } catch (e: Exception) {
          return
        }
    }

    private fun downloadWeather(): JSONObject {
        if (DefaultParameter.CITY.isNotBlank() && DefaultParameter.COUNTRY.isNotBlank()) {
            val woeid = WeatherDownloader.getWoeidByCityAndCountry(DefaultParameter.CITY, DefaultParameter.COUNTRY)
            if (woeid.isNotBlank()) {
                return WeatherDownloader.getWeatherByWoeid(woeid)
            }
        }
        return WeatherDownloader.getWeatherByLatitudeAndLongitude(DefaultParameter.LOCALIZATION_LATITUDE.toString(),
                DefaultParameter.LOCALIZATION_LONGITUDE.toString())
    }

    private fun getFormattedTime(localTime: String): String {
        val time = localTime
                .split("\\s+".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
        return time[time.size-3] + " " + time[time.size-2]
    }

    private fun getCurrentConditionFromDescription(description: String): String {
        val firstIndex = description.indexOf("Current Conditions:")
        val lastIndex = description.indexOf("Forecast")
        return description
                .substring(firstIndex + 19, lastIndex)
                .replace("</b>", "")
                .replace("<b>", "")
                .replace("<BR />", "")
                .replace(":", "")
                .trim()
    }

    private fun getIconNumberFromDescription(description: String) =
            (48 downTo 0)
            .firstOrNull { description.contains(it.toString() + ".gif") }
            ?.toString()
            ?: DefaultParameter.ICON_NUMBER
}
