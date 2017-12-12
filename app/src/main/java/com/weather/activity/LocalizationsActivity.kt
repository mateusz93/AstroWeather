package com.weather.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import com.weather.R
import com.weather.db.Localization


/**
 * @author Mateusz Wieczorek
 */
class LocalizationsActivity : Activity() {

    private val DELETE_BUTTON_INTERVAL = 1000000

    private val linearLayout: LinearLayout
        get() {
            val newLinearLayout = LinearLayout(this)
            newLinearLayout.orientation = LinearLayout.HORIZONTAL
            newLinearLayout.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            newLinearLayout.orientation = LinearLayout.HORIZONTAL
            return newLinearLayout
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.localizations)
        initOnClicks()
    }

    override fun onBackPressed() {
        val intent = Intent(this@LocalizationsActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initOnClicks() {
        val newLocationButton = findViewById<Button>(R.id.newLocation)
        newLocationButton.setOnClickListener { v ->
            val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val intent = Intent(this@LocalizationsActivity, NewLocalizationActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setEditButtonsOnClick(editButton: Button, editButtonId: Int) {
        editButton.setOnClickListener { v ->
            val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vb.vibrate(50)
            val intent = Intent(this@LocalizationsActivity, NewLocalizationActivity::class.java)
            intent.putExtra("id", editButtonId)
            startActivity(intent)
        }
    }

    private fun getNameView(localization: Localization): TextView {
        val nameView = TextView(this)
        nameView.textSize = 20f
        nameView.setTextColor(Color.WHITE)
        nameView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
        nameView.text = localization.name
        return nameView
    }

    private fun getDeleteButton(localization: Localization): Button {
        val deleteButton = Button(this)
        deleteButton.text = resources.getString(R.string.delete)
        deleteButton.setBackgroundColor(Color.WHITE)
        deleteButton.id = Integer.parseInt(localization.id) + DELETE_BUTTON_INTERVAL
        return deleteButton
    }

    private fun getEditButton(localization: Localization): Button {
        val editButton = Button(this)
        editButton.text = resources.getString(R.string.edit)
        editButton.id = Integer.parseInt(localization.id)
        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 20, 0)
        editButton.layoutParams = params
        editButton.setBackgroundColor(Color.WHITE)
        return editButton
    }
}
