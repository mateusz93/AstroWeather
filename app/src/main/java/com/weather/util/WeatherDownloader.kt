package com.weather.util

import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author Mateusz Wieczorek
 */
object WeatherDownloader {

    @Throws(IOException::class, JSONException::class)
    fun getWoeidByCityAndCountry(city2: String, country: String): String {
        var city = escape(city2)
        val result = StringBuilder()
        val url = URL(escape("https://query.yahooapis.com/v1/public/yql?q=" +
                "select * from geo.places(1) where text=\"" + city + ", " + country + "\"&format=json&lang=pl-PL"))
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line: String?
        while (true) {
            line = rd.readLine()
            if (line == null) {
                break
            }
            result.append(line)
        }
        rd.close()
        city = unEscape(city)
        val json = JSONObject(result.toString())
        return if (!city.equals(json.getJSONObject("query").getJSONObject("results").getJSONObject("place").getString("name"), ignoreCase = true)) {
            throw JSONException("IncorrectCityOrCountry")
        } else {
            json.getJSONObject("query").getJSONObject("results").getJSONObject("place").getString("woeid")
        }
    }

    @Throws(IOException::class, JSONException::class)
    fun getWoeidByLatitudeAndLongitude(latitude: String, longitude: String): String {
        val result = StringBuilder()
        val url = URL(escape("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where " +
                "woeid in (SELECT woeid FROM geo.places WHERE text=\"(" + latitude + "," + longitude + ")\") " +
                "and u=\"c\"&format=json&lang=pl-pl"))
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line: String?
        while (true) {
            line = rd.readLine()
            if (line == null) {
                break
            }
            result.append(line)
        }
        rd.close()
        val json = JSONObject(result.toString())

        val city = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("city")
        val country = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("country")

        return getWoeidByCityAndCountry(city, country)

    }

    @Throws(IOException::class, JSONException::class)
    fun getWeatherByWoeid(woeid: String): JSONObject {
        val result = StringBuilder()
        val url = URL(escape("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast " +
                "where woeid=" + woeid + " and u=\"c\"&format=json&lang=pl-pl"))
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line: String?
        while (true) {
            line = rd.readLine()
            if (line == null) {
                break
            }
            result.append(line)
        }
        rd.close()
        val json = JSONObject(result.toString())
        return json.getJSONObject("query").getJSONObject("results").getJSONObject("channel")

    }

    @Throws(IOException::class, JSONException::class)
    fun getWeatherByLatitudeAndLongitude(latitude: String, longitude: String): JSONObject {
        val result = StringBuilder()
        val url = URL(escape("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where " +
                "woeid in (SELECT woeid FROM geo.places WHERE text=\"(" + latitude + "," + longitude + ")\") " +
                "and u=\"c\"&format=json&lang=pl-pl"))
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line: String?
        while (true) {
            line = rd.readLine()
            if (line == null) {
                break
            }
            result.append(line)
        }
        rd.close()
        val json = JSONObject(result.toString())
        return json.getJSONObject("query").getJSONObject("results").getJSONObject("channel")

    }

    fun escape(text: String): String {
        return text
                .replace(" ", "%20")
                .replace("\"", "%22")
    }

    fun unEscape(text: String): String {
        return text
                .replace("%20", " ")
                .replace("%22", "\"")
    }
}