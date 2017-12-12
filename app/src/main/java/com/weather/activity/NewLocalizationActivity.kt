package com.weather.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.weather.R
import com.weather.util.DefaultParameter

/**
 * @author Mateusz Wieczorek
 */
class NewLocalizationActivity : Activity() {

    private var extras: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_localization)
        extras = intent.extras
        initOnClicks()
    }

    override fun onBackPressed() {
        val intent = Intent(this@NewLocalizationActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initOnClicks() {
        val saveButton = findViewById<Button>(R.id.addNewLocalizationButton)

        saveButton.setOnClickListener {
            val country = (findViewById<View>(R.id.LocalizationCountryValue) as EditText).text.toString()
            val city = (findViewById<View>(R.id.LocalizationCityValue) as EditText).text.toString()
            val latitude = (findViewById<View>(R.id.LocalizationLatituteValue) as EditText).text.toString().toDouble()
            val longitude = (findViewById<View>(R.id.LocalizationLongitudeValue) as EditText).text.toString().toDouble()

            updateLocalization(city, country, latitude, longitude)
            goToMainActivity()
        }
    }

    private fun updateLocalization(city: String, country: String, latitude: Double, longitude: Double) {
        DefaultParameter.LOCALIZATION_LATITUDE = latitude
        DefaultParameter.LOCALIZATION_LATITUDE = longitude
        DefaultParameter.CITY = city
        DefaultParameter.COUNTRY = country
    }

    private fun goToMainActivity() {
        val intent = Intent(this@NewLocalizationActivity, MainActivity::class.java)
        Toast.makeText(this@NewLocalizationActivity, resources.getString(R.string.localizationSaved), Toast.LENGTH_LONG).show()
        startActivity(intent)
    }
}
