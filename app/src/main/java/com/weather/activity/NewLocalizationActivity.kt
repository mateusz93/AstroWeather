package com.weather.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.weather.R
import com.weather.db.Localization
import com.weather.task.DownloadWoeidByCityAndCountryTask
import com.weather.task.DownloadWoeidByLatitudeAndLongitudeTask
import com.weather.util.WeatherDownloader

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.Date
import java.util.concurrent.ExecutionException


/**
 * @author Mateusz Wieczorek
 */
class NewLocalizationActivity : Activity() {

    private var extras: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_localization)
        extras = intent.extras
        if (extras != null) {
            setFields(extras!!)
        }
        initOnClicks()
    }

    override fun onBackPressed() {
        val intent = Intent(this@NewLocalizationActivity, LocalizationsActivity::class.java)
        startActivity(intent)
    }

    private fun setFields(extras: Bundle) {
        val localizationId = extras.get("id") as Int
        val name = findViewById<EditText>(R.id.LocalizationNameValue)
        val country = findViewById<EditText>(R.id.LocalizationCountryValue)
        val city = findViewById<EditText>(R.id.LocalizationCityValue)
        val latitude = findViewById<EditText>(R.id.LocalizationLatituteValue)
        val longitude = findViewById<EditText>(R.id.LocalizationLongitudeValue)
        name.setText("localization name")
        name.isEnabled = false
        country.setText("country")
        city.setText("city")
        latitude.setText("latitute")
        longitude.setText("longitude")
    }

    private fun initOnClicks() {
        val saveButton = findViewById<Button>(R.id.addNewLocalizationButton)

        saveButton.setOnClickListener { v ->
            val name = (findViewById<View>(R.id.LocalizationNameValue) as EditText).text.toString()
            val country = (findViewById<View>(R.id.LocalizationCountryValue) as EditText).text.toString()
            val city = (findViewById<View>(R.id.LocalizationCityValue) as EditText).text.toString()
            val latitude = (findViewById<View>(R.id.LocalizationLatituteValue) as EditText).text.toString()
            val longitude = (findViewById<View>(R.id.LocalizationLongitudeValue) as EditText).text.toString()

            downloadWeather(name, city, country, latitude, longitude)
        }
    }

    private fun downloadWeather(name: String, city: String, country: String, latitude: String, longitude: String) {
        val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        try {
            var woeid: String? = DownloadWoeidByCityAndCountryTask().execute(city, country).get()
            if (woeid == null) {
                woeid = DownloadWoeidByLatitudeAndLongitudeTask().execute(latitude, longitude).get()
                if (woeid == null) {
                    Toast.makeText(this@NewLocalizationActivity, resources.getString(R.string.incorrectLocalization), Toast.LENGTH_LONG).show()
                    return
                }
            }
            saveLocation(name, woeid)

            val intent = Intent(this@NewLocalizationActivity, LocalizationsActivity::class.java)
            Toast.makeText(this@NewLocalizationActivity, resources.getString(R.string.localizationSaved), Toast.LENGTH_LONG).show()
            startActivity(intent)

        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

    }

    private fun saveLocation(name: String, woeid: String) {
        try {
            val jsonWeather = WeatherDownloader.getWeatherByWoeid(woeid)

            val latitute = jsonWeather.getJSONObject("item").getString("lat")
            val longitude = jsonWeather.getJSONObject("item").getString("long")
            val city = jsonWeather.getJSONObject("location").getString("city")
            val country = jsonWeather.getJSONObject("location").getString("country")
            val weather = jsonWeather.toString()
            val weatherForecast = jsonWeather.getJSONObject("item").getJSONArray("forecast").toString()
            val lastUpdate = Date().time.toString()

            val localization = Localization()
            localization.name = name
            localization.woeid = woeid
            localization.latitude = latitute
            localization.longitude = longitude
            localization.city = city
            localization.country = country
            localization.weather = weather
            localization.forecast = weatherForecast
            localization.lastUpdate = lastUpdate
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
