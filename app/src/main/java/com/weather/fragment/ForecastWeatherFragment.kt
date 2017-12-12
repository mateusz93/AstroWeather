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

import org.json.JSONArray
import org.json.JSONException

/**
 * @author Mateusz Wieczorek
 */
class ForecastWeatherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.forecast_weather_fragment, container, false)
        createThread(rootView)
        //thread.start();
        return rootView
    }

    private fun createThread(rootView: View) {
        activity.runOnUiThread { setValues(rootView) }
    }

    override fun onStop() {
        super.onStop()
        //thread.interrupt();
    }

    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private fun setValues(rootView: View) {
        try {
            val weatherForecast: JSONArray? = null
            //            setIcons(rootView, weatherForecast);
            //            setDates(rootView, weatherForecast);
            //            setTemperatures(rootView, weatherForecast);
            //            setDescriptions(rootView, weatherForecast);
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    private fun setDescriptions(rootView: View, weatherForecast: JSONArray) {
        val forecastDescription1 = rootView.findViewById<TextView>(R.id.forecastDay1DescriptionValue)
        forecastDescription1.text = getForecastDescription(weatherForecast, 1)

        val forecastDescription2 = rootView.findViewById<TextView>(R.id.forecastDay2DescriptionValue)
        forecastDescription2.text = getForecastDescription(weatherForecast, 2)

        val forecastDescription3 = rootView.findViewById<TextView>(R.id.forecastDay3DescriptionValue)
        forecastDescription3.text = getForecastDescription(weatherForecast, 3)

        val forecastDescription4 = rootView.findViewById<TextView>(R.id.forecastDay4DescriptionValue)
        forecastDescription4.text = getForecastDescription(weatherForecast, 4)

        val forecastDescription5 = rootView.findViewById<TextView>(R.id.forecastDay5DescriptionValue)
        forecastDescription5.text = getForecastDescription(weatherForecast, 5)

        val forecastDescription6 = rootView.findViewById<TextView>(R.id.forecastDay6DescriptionValue)
        forecastDescription6.text = getForecastDescription(weatherForecast, 6)

        val forecastDescription7 = rootView.findViewById<TextView>(R.id.forecastDay7DescriptionValue)
        forecastDescription7.text = getForecastDescription(weatherForecast, 7)
    }

    //    private void setTemperatures(View rootView, JSONArray weatherForecast) {
    //        TextView forecastTemperature1 = (TextView) rootView.findViewById(R.id.forecastDay1TemperatureValue);
    //        forecastTemperature1.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 1))
    //                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 1)) + Parameter.TEMPERATURE_UNIT);
    //
    //        TextView forecastTemperature2 = (TextView) rootView.findViewById(R.id.forecastDay2TemperatureValue);
    //        forecastTemperature2.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 2))
    //                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 2)) + Parameter.TEMPERATURE_UNIT);
    //
    //        TextView forecastTemperature3 = (TextView) rootView.findViewById(R.id.forecastDay3TemperatureValue);
    //        forecastTemperature3.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 3))
    //                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 3)) + Parameter.TEMPERATURE_UNIT);
    //
    //        TextView forecastTemperature4 = (TextView) rootView.findViewById(R.id.forecastDay4TemperatureValue);
    //        forecastTemperature4.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 4))
    //                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 4)) + Parameter.TEMPERATURE_UNIT);
    //
    //        TextView forecastTemperature5 = (TextView) rootView.findViewById(R.id.forecastDay5TemperatureValue);
    //        forecastTemperature5.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 5))
    //                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 5)) + Parameter.TEMPERATURE_UNIT);
    //
    //        TextView forecastTemperature6 = (TextView) rootView.findViewById(R.id.forecastDay6TemperatureValue);
    //        forecastTemperature6.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 6))
    //                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 6)) + Parameter.TEMPERATURE_UNIT);
    //
    //        TextView forecastTemperature7 = (TextView) rootView.findViewById(R.id.forecastDay7TemperatureValue);
    //        forecastTemperature7.setText(getConverterTemperature(getForecastLowTemperature(weatherForecast, 7))
    //                + " - " + getConverterTemperature(getForecastHighTemperature(weatherForecast, 7)) + Parameter.TEMPERATURE_UNIT);
    //    }

    private fun setDates(rootView: View, weatherForecast: JSONArray) {
        val forecastDate1 = rootView.findViewById<TextView>(R.id.forecastDay1DateValue)
        forecastDate1.text = getForecastDay(weatherForecast, 1) + ", " + getForecastDate(weatherForecast, 1)

        val forecastDate2 = rootView.findViewById<TextView>(R.id.forecastDay2DateValue)
        forecastDate2.text = getForecastDay(weatherForecast, 2) + ", " + getForecastDate(weatherForecast, 2)

        val forecastDate3 = rootView.findViewById<TextView>(R.id.forecastDay3DateValue)
        forecastDate3.text = getForecastDay(weatherForecast, 3) + ", " + getForecastDate(weatherForecast, 3)

        val forecastDate4 = rootView.findViewById<TextView>(R.id.forecastDay4DateValue)
        forecastDate4.text = getForecastDay(weatherForecast, 4) + ", " + getForecastDate(weatherForecast, 4)

        val forecastDate5 = rootView.findViewById<TextView>(R.id.forecastDay5DateValue)
        forecastDate5.text = getForecastDay(weatherForecast, 5) + ", " + getForecastDate(weatherForecast, 5)

        val forecastDate6 = rootView.findViewById<TextView>(R.id.forecastDay6DateValue)
        forecastDate6.text = getForecastDay(weatherForecast, 6) + ", " + getForecastDate(weatherForecast, 6)

        val forecastDate7 = rootView.findViewById<TextView>(R.id.forecastDay7DateValue)
        forecastDate7.text = getForecastDay(weatherForecast, 7) + ", " + getForecastDate(weatherForecast, 7)
    }

    private fun setIcons(rootView: View, weatherForecast: JSONArray) {
        val forecastIcon1 = rootView.findViewById<ImageView>(R.id.forecastDay1Icon)
        var iconNumber = getForecastIconNumber(weatherForecast, 1)
        var id = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
        forecastIcon1.setImageResource(id)

        val forecastIcon2 = rootView.findViewById<ImageView>(R.id.forecastDay2Icon)
        iconNumber = getForecastIconNumber(weatherForecast, 2)
        id = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
        forecastIcon2.setImageResource(id)

        val forecastIcon3 = rootView.findViewById<ImageView>(R.id.forecastDay3Icon)
        iconNumber = getForecastIconNumber(weatherForecast, 3)
        id = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
        forecastIcon3.setImageResource(id)

        val forecastIcon4 = rootView.findViewById<ImageView>(R.id.forecastDay4Icon)
        iconNumber = getForecastIconNumber(weatherForecast, 4)
        id = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
        forecastIcon4.setImageResource(id)

        val forecastIcon5 = rootView.findViewById<ImageView>(R.id.forecastDay5Icon)
        iconNumber = getForecastIconNumber(weatherForecast, 5)
        id = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
        forecastIcon5.setImageResource(id)

        val forecastIcon6 = rootView.findViewById<ImageView>(R.id.forecastDay6Icon)
        iconNumber = getForecastIconNumber(weatherForecast, 6)
        id = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
        forecastIcon6.setImageResource(id)

        val forecastIcon7 = rootView.findViewById<ImageView>(R.id.forecastDay7Icon)
        iconNumber = getForecastIconNumber(weatherForecast, 7)
        id = resources.getIdentifier("icon_" + iconNumber, "drawable", context.packageName)
        forecastIcon7.setImageResource(id)
    }

    private fun getForecastIconNumber(weather: JSONArray, dayNumber: Int): String {
        return getForecastItem(weather, dayNumber, "code")
    }

    private fun getForecastDate(weather: JSONArray, dayNumber: Int): String {
        return getForecastItem(weather, dayNumber, "date")
    }

    private fun getForecastDay(weather: JSONArray, dayNumber: Int): String {
        return getForecastItem(weather, dayNumber, "day")
    }

    private fun getForecastHighTemperature(weather: JSONArray, dayNumber: Int): String {
        return getForecastItem(weather, dayNumber, "high")
    }

    private fun getForecastLowTemperature(weather: JSONArray, dayNumber: Int): String {
        return getForecastItem(weather, dayNumber, "low")
    }

    private fun getForecastDescription(weather: JSONArray, dayNumber: Int): String {
        return getForecastItem(weather, dayNumber, "text")
    }

    private fun getForecastItem(weather: JSONArray, dayNumber: Int, item: String): String {
        try {
            return weather.getJSONObject(dayNumber).getString(item)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ""
    }

    companion object {

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(): ForecastWeatherFragment {
            val fragment = ForecastWeatherFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
