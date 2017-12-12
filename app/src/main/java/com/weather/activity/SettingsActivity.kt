package com.weather.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

import com.weather.R
import com.weather.util.Parameter

/**
 * @author Mateusz Wieczorek
 */
class SettingsActivity : Activity() {

    private var refreshingValue: EditText? = null
    private var saveButton: Button? = null
    private var kilometerRadioButton: RadioButton? = null
    private var mileRadioButton: RadioButton? = null
    private var celsiusRadioButton: RadioButton? = null
    private var kelvinRadioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        init()
        initOnClicks()
    }

    override fun onBackPressed() {
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun init() {
        refreshingValue = findViewById(R.id.refreshingValue)
        saveButton = findViewById(R.id.saveButton)
        celsiusRadioButton = findViewById(R.id.celsiusRadioButton)
        kelvinRadioButton = findViewById(R.id.kelvinRadioButton)
        kilometerRadioButton = findViewById(R.id.kilometerRadioButton)
        mileRadioButton = findViewById(R.id.mileRadioButton)

        setTemperatureUnits()
        setSpeedUnit()

        val city = findViewById<TextView>(R.id.settingsCity)
        city.text = resources.getString(R.string.settings_city) + ": " + Parameter.LOCALIZATION_NAME.toString()
        refreshingValue!!.setText(Parameter.REFRESH_INTERVAL_IN_SEC.toString())
    }

    private fun initOnClicks() {
        saveButton!!.setOnClickListener { v ->
            val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            Parameter.REFRESH_INTERVAL_IN_SEC = Integer.valueOf(refreshingValue!!.text.toString())!!
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)
        }

        kilometerRadioButton!!.setOnClickListener { v -> val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

        mileRadioButton!!.setOnClickListener { v -> val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

        celsiusRadioButton!!.setOnClickListener { v -> val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

        kelvinRadioButton!!.setOnClickListener { v -> val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    }

    private fun setSpeedUnit() {
        if (Parameter.SPEED_UNIT == "km/h") {
            kilometerRadioButton!!.isChecked = true
            mileRadioButton!!.isChecked = false
        } else {
            kilometerRadioButton!!.isChecked = false
            mileRadioButton!!.isChecked = true
        }
    }

    private fun setTemperatureUnits() {
        if (Parameter.TEMPERATURE_UNIT == "°C") {
            celsiusRadioButton!!.isChecked = true
            kelvinRadioButton!!.isChecked = false
        } else {
            celsiusRadioButton!!.isChecked = false
            kelvinRadioButton!!.isChecked = true
        }
    }

}
