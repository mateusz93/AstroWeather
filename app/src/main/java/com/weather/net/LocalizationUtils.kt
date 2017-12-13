package com.weather.net

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.weather.util.DefaultParameter

/**
 * @author Mateusz Wieczorek
 */
class LocalizationUtils : LocationListener {

    private val MIN_DISTANCE_CHANGE_FOR_UPDATES = 10.0 // 10 meters
    private val MIN_TIME_BW_UPDATES = (1000 * 10 * 1).toLong() // 10 seconds

    fun updateLocalizationByGPSorNetwork(context: Activity) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled && !isNetworkEnabled) {
            return
        }
        if (isGPSEnabled) {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            updateLocalizationByGPS(locationManager)
        }
        if (isNetworkEnabled) {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            updateLocalizationByNetwork(locationManager)
        }
    }

    private fun updateLocalizationByGPS(locationManager: LocationManager) {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                this)
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            DefaultParameter.LOCALIZATION_LATITUDE = location.latitude
            DefaultParameter.LOCALIZATION_LONGITUDE = location.longitude
        }
    }

    private fun updateLocalizationByNetwork(locationManager: LocationManager) {
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                this)
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            DefaultParameter.LOCALIZATION_LATITUDE = location.latitude
            DefaultParameter.LOCALIZATION_LONGITUDE = location.longitude
        }
    }

    override fun onLocationChanged(location: Location?) {
        if (!DefaultParameter.IS_CUSTOM_LOCALIZATION && location != null) {
            DefaultParameter.LOCALIZATION_LATITUDE = location.latitude
            DefaultParameter.LOCALIZATION_LONGITUDE = location.longitude
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(p0: String?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(p0: String?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}