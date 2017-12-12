package com.weather.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

import com.weather.R
import com.weather.SectionsPagerAdapter

import java.text.DateFormat
import java.util.Date

/**
 * @author Mateusz Wieczorek
 */
class MainActivity : AppCompatActivity() {

    private var timeThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
    }

    private fun initLayout() {
        val viewPager = findViewById<ViewPager>(R.id.container)
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        timeThread = createTimeThread()
        timeThread!!.start()
    }


    private fun createTimeThread(): Thread {
        return object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        runOnUiThread {
                            val time = findViewById<TextView>(R.id.time)
                            if (time != null) {
                                time.text = DateFormat.getDateTimeInstance().format(Date())
                            }
                        }
                        Thread.sleep(1000)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }

        if (id == R.id.action_localizations) {
            val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val intent = Intent(this@MainActivity, LocalizationsActivity::class.java)
            startActivity(intent)
            return true
        }

        if (id == R.id.action_refresh) {
            val vb = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        timeThread!!.interrupt()
        delegate.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

}
