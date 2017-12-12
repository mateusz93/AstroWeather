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
import com.weather.util.WeatherDownloader
import org.json.JSONException
import java.io.IOException
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.weather_fragment, container, false)
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
            val weather = WeatherDownloader.getWeatherByLatitudeAndLongitude(DefaultParameter.LOCALIZATION_LATITUDE.toString(),
                                                                             DefaultParameter.LOCALIZATION_LONGITUDE.toString())

            val city = weather.getJSONObject("location").getString("city")
            val latitude = DefaultParameter.LOCALIZATION_LATITUDE.toString()
            val longitude = DefaultParameter.LOCALIZATION_LONGITUDE.toString()
            val temperature = weather.getJSONObject("item").getJSONObject("condition").getString("temp")
            val windDirection = weather.getJSONObject("wind").getString("direction")
            val windSpeed = weather.getJSONObject("wind").getString("speed")
            val atmosphereHumidity = weather.getJSONObject("atmosphere").getString("humidity")
            val atmosphereVisibility = weather.getJSONObject("atmosphere").getString("visibility")
            val atmospherePressure = weather.getJSONObject("atmosphere").getString("pressure")
            var description = weather.getJSONObject("item").getString("description")
            val iconNumber = getIconNumberFromDescription(description)
            description = getCurrentConditionFromDescription(description)
            var localTime = weather.getString("lastBuildDate")
            localTime = getFormattedTime(localTime)

            val weatherIcon = rootView.findViewById<ImageView>(R.id.weatherIcon)
            val id = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
            weatherIcon.setImageResource(id)

            val cityValueView = rootView.findViewById<TextView>(R.id.weatherCityValue)
            cityValueView.text = city

            val latitudeValueView = rootView.findViewById<TextView>(R.id.weatherLatitudeValue)
            latitudeValueView.text = latitude

            val longitudeValueView = rootView.findViewById<TextView>(R.id.weatherLongitudeValue)
            longitudeValueView.text = longitude

            val temperatureViewView = rootView.findViewById<TextView>(R.id.weatherTemperatureValue)
            temperatureViewView.text = temperature + DefaultParameter.TEMPERATURE_UNIT

            val localTimeValueView = rootView.findViewById<TextView>(R.id.weatherLocalTimeValue)
            localTimeValueView.text = localTime

            val windDirectionValueView = rootView.findViewById<TextView>(R.id.weatherWindDirectionValue)
            windDirectionValueView.text = windDirection

            val windSpeedValueView = rootView.findViewById<TextView>(R.id.weatherWindSpeedValue)
            windSpeedValueView.text = windSpeed + DefaultParameter.SPEED_UNIT

            val atmosphereHumidityValueView = rootView.findViewById<TextView>(R.id.weatherAtmosphereHumidityValue)
            atmosphereHumidityValueView.text = atmosphereHumidity + "%"

            val atmosphereVisibilityValueView = rootView.findViewById<TextView>(R.id.weatherAtmosphereVisibilityValue)
            atmosphereVisibilityValueView.text = atmosphereVisibility

            val atmospherePressureValueView = rootView.findViewById<TextView>(R.id.weatherAtmospherePressureValue)
            atmospherePressureValueView.text = atmospherePressure + DefaultParameter.PRESSURE_UNIT

            val descriptionValueView = rootView.findViewById<TextView>(R.id.weatherDescriptionValue)
            descriptionValueView.text = description
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getFormattedTime(localTime: String): String {
        val time = localTime
                .split("\\s+".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
        var formattedTime = ""
        for (i in 0 until time.size - 2) {
            formattedTime += time[i] + " "
        }
        return formattedTime.trim { it <= ' ' }
    }

    private fun getCurrentConditionFromDescription(description: String): String {
        val firstIndex = description.indexOf("Current Conditions:")
        val lastIndex = description.indexOf("Forecast")
        return description
                .substring(firstIndex + 19, lastIndex)
                .replace("</b>".toRegex(), "")
                .replace("<b>".toRegex(), "")
                .replace("<BR />".toRegex(), "")
                .replace(":".toRegex(), "")
                .trim { it <= ' ' }
    }

    private fun getIconNumberFromDescription(description: String): String {
        return (48 downTo 0)
                .firstOrNull { description.contains(it.toString() + ".gif") }
                ?.toString()
                ?: DefaultParameter.ICON_NUMBER
    }
}
