package com.weather.task

import android.os.AsyncTask
import com.weather.util.WeatherDownloader
import org.json.JSONException
import java.io.IOException


/**
 * @author Mateusz Wieczorek
 */
class DownloadWoeidByCityAndCountryTask : AsyncTask<String, String, String>() {

    override fun doInBackground(vararg params: String): String? {
        try {
            return WeatherDownloader.getWoeidByCityAndCountry(params[0], params[1])
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return null
    }
}
