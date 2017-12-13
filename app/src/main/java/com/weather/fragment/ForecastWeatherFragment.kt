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
import org.json.JSONArray
/**
 * @author Mateusz Wieczorek
 */
class ForecastWeatherFragment : Fragment() {

    companion object {
        fun newInstance(): ForecastWeatherFragment {
            val fragment = ForecastWeatherFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.forecast_weather_fragment, container, false)
        activity.runOnUiThread { setValues(rootView) }
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private fun setValues(rootView: View) {
        val weatherForecast = WeatherDownloader.getWeatherByLatitudeAndLongitude(DefaultParameter.LOCALIZATION_LATITUDE.toString(),
                                                                                 DefaultParameter.LOCALIZATION_LONGITUDE.toString())
            .getJSONObject("item")
            .getJSONArray("forecast")
        setIcons(rootView, weatherForecast)
        setDates(rootView, weatherForecast)
        setTemperatures(rootView, weatherForecast)
        setDescriptions(rootView, weatherForecast)
    }

    private fun setDescriptions(rootView: View, weatherForecast: JSONArray) {
        rootView.findViewById<TextView>(R.id.forecastDay1DescriptionValue)
                .text = getForecastDescription(weatherForecast, 1)

        rootView.findViewById<TextView>(R.id.forecastDay2DescriptionValue)
                .text = getForecastDescription(weatherForecast, 2)

        rootView.findViewById<TextView>(R.id.forecastDay3DescriptionValue)
                .text = getForecastDescription(weatherForecast, 3)

        rootView.findViewById<TextView>(R.id.forecastDay4DescriptionValue)
                .text = getForecastDescription(weatherForecast, 4)

        rootView.findViewById<TextView>(R.id.forecastDay5DescriptionValue)
                .text = getForecastDescription(weatherForecast, 5)

        rootView.findViewById<TextView>(R.id.forecastDay6DescriptionValue)
                .text = getForecastDescription(weatherForecast, 6)

        rootView.findViewById<TextView>(R.id.forecastDay7DescriptionValue)
                .text = getForecastDescription(weatherForecast, 7)
    }

    private fun setTemperatures(rootView: View, weatherForecast: JSONArray) {
        rootView.findViewById<TextView>(R.id.forecastDay1TemperatureValue)
                .text = generateTemperatureText(weatherForecast, 1)

        rootView.findViewById<TextView>(R.id.forecastDay2TemperatureValue)
                .text = generateTemperatureText(weatherForecast, 2)

        rootView.findViewById<TextView>(R.id.forecastDay3TemperatureValue)
                .text = generateTemperatureText(weatherForecast, 3)

        rootView.findViewById<TextView>(R.id.forecastDay4TemperatureValue)
                .text = generateTemperatureText(weatherForecast, 4)

        rootView.findViewById<TextView>(R.id.forecastDay5TemperatureValue)
                .text = generateTemperatureText(weatherForecast, 5)

        rootView.findViewById<TextView>(R.id.forecastDay6TemperatureValue)
                .text = generateTemperatureText(weatherForecast, 6)

        rootView.findViewById<TextView>(R.id.forecastDay7TemperatureValue)
                .text = generateTemperatureText(weatherForecast, 7)
    }

    private fun setDates(rootView: View, weatherForecast: JSONArray) {
        rootView.findViewById<TextView>(R.id.forecastDay1DateValue)
                .text = generateDateText(weatherForecast, 1)

        rootView.findViewById<TextView>(R.id.forecastDay2DateValue)
                .text = generateDateText(weatherForecast, 2)

        rootView.findViewById<TextView>(R.id.forecastDay3DateValue)
                .text = generateDateText(weatherForecast, 3)

        rootView.findViewById<TextView>(R.id.forecastDay4DateValue)
                .text = generateDateText(weatherForecast, 4)

        rootView.findViewById<TextView>(R.id.forecastDay5DateValue)
                .text = generateDateText(weatherForecast, 5)

        rootView.findViewById<TextView>(R.id.forecastDay6DateValue)
                .text = generateDateText(weatherForecast, 6)

        rootView.findViewById<TextView>(R.id.forecastDay7DateValue)
                .text = generateDateText(weatherForecast, 7)
    }

    private fun setIcons(rootView: View, weatherForecast: JSONArray) {
        rootView.findViewById<ImageView>(R.id.forecastDay1Icon).setImageResource(generateImageResource(weatherForecast, 1))
        rootView.findViewById<ImageView>(R.id.forecastDay2Icon).setImageResource(generateImageResource(weatherForecast, 2))
        rootView.findViewById<ImageView>(R.id.forecastDay3Icon).setImageResource(generateImageResource(weatherForecast, 3))
        rootView.findViewById<ImageView>(R.id.forecastDay4Icon).setImageResource(generateImageResource(weatherForecast, 4))
        rootView.findViewById<ImageView>(R.id.forecastDay5Icon).setImageResource(generateImageResource(weatherForecast, 5))
        rootView.findViewById<ImageView>(R.id.forecastDay6Icon).setImageResource(generateImageResource(weatherForecast, 6))
        rootView.findViewById<ImageView>(R.id.forecastDay7Icon).setImageResource(generateImageResource(weatherForecast, 7))
    }

    private fun generateDateText(weatherForecast: JSONArray, dayNumber: Int) =
        getForecastDay(weatherForecast, dayNumber) + ", " +
        getForecastDate(weatherForecast, dayNumber)

    private fun generateTemperatureText(weatherForecast: JSONArray, dayNumber: Int) =
        getForecastLowTemperature(weatherForecast, dayNumber) + "  -  " +
        getForecastHighTemperature(weatherForecast, dayNumber) +
        DefaultParameter.TEMPERATURE_UNIT

    private fun generateImageResource(weatherForecast: JSONArray, dayNumber: Int): Int {
        val iconNumber = getForecastIconNumber(weatherForecast, dayNumber)
        return resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
    }

    private fun getForecastIconNumber(weather: JSONArray, dayNumber: Int) = getForecastItem(weather, dayNumber, "code")
    private fun getForecastDate(weather: JSONArray, dayNumber: Int) = getForecastItem(weather, dayNumber, "date")
    private fun getForecastDay(weather: JSONArray, dayNumber: Int) = getForecastItem(weather, dayNumber, "day")
    private fun getForecastHighTemperature(weather: JSONArray, dayNumber: Int) = getForecastItem(weather, dayNumber, "high")
    private fun getForecastLowTemperature(weather: JSONArray, dayNumber: Int) = getForecastItem(weather, dayNumber, "low")
    private fun getForecastDescription(weather: JSONArray, dayNumber: Int) = getForecastItem(weather, dayNumber, "text")
    private fun getForecastItem(weather: JSONArray, dayNumber: Int, item: String) = weather.getJSONObject(dayNumber).getString(item)

}
