package com.weather.task

import android.os.AsyncTask

import com.weather.util.WeatherDownloader

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


/**
 * @author Mateusz Wieczorek
 */
class WeatherDownloadByLatitudeAndLongitudeTask : AsyncTask<String, String, JSONObject>() {

    override fun doInBackground(vararg params: String): JSONObject? {
        try {
            return WeatherDownloader.getWeatherByLatitudeAndLongitude(params[0], params[1])
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return null
    }
}
