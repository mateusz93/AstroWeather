package com.weather.net

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
    fun getWoeidByCityAndCountry(newCity: String, country: String): String {
        var city = escape(newCity)
        val url = URL(escape("https://query.yahooapis.com/v1/public/yql?q=" +
                "select * from geo.places(1) where text=\"" + city + ", " + country + "\"&format=json&lang=pl-PL"))
        city = unEscape(city)
        val json = JSONObject(readResponse(url))
        return if (!city.equals(json.getJSONObject("query").getJSONObject("results").getJSONObject("place").getString("name"), ignoreCase = true)) {
            throw JSONException("IncorrectCityOrCountry")
        } else {
            json.getJSONObject("query").getJSONObject("results").getJSONObject("place").getString("woeid")
        }
    }

    @Throws(IOException::class, JSONException::class)
    fun getWoeidByLatitudeAndLongitude(latitude: String, longitude: String): String {
        val url = URL(escape("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where " +
                "woeid in (SELECT woeid FROM geo.places WHERE text=\"(" + latitude + "," + longitude + ")\") " +
                "and u=\"c\"&format=json&lang=pl-pl"))
        val json = JSONObject(readResponse(url))
        val city = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("city")
        val country = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("country")
        return getWoeidByCityAndCountry(city, country)
    }

    @Throws(IOException::class, JSONException::class)
    fun getWeatherByWoeid(woeid: String): JSONObject {
        val url = URL(escape("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast " +
                "where woeid=" + woeid + " and u=\"c\"&format=json&lang=pl-pl"))
        val json = JSONObject(readResponse(url))
        return json.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
    }

    @Throws(IOException::class, JSONException::class)
    fun getWeatherByLatitudeAndLongitude(latitude: String, longitude: String): JSONObject {
        val url = URL(escape("https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where " +
                "woeid in (SELECT woeid FROM geo.places WHERE text=\"(" + latitude + "," + longitude + ")\") " +
                "and u=\"c\"&format=json&lang=pl-pl"))
        val json = JSONObject(readResponse(url))
        return json.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
    }

    private fun readResponse(url: URL) : String {
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        val result = StringBuilder()
        var line: String?
        while (true) {
            line = rd.readLine()
            if (line == null) {
                break
            }
            result.append(line)
        }
        rd.close()
        return result.toString()
    }

    fun escape(text: String) = text
            .replace(" ", "%20")
            .replace("\"", "%22")

    fun unEscape(text: String) = text
            .replace("%20", " ")
            .replace("%22", "\"")
}