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
        var city = city2
        val result = StringBuilder()
        var url: URL? = null
        city = city.replace(" ", "%20")
        url = URL("https://query.yahooapis.com/v1/public/yql?q=" +
                "select%20*%20from%20geo.places(1)%20where%20text=%22" + city +
                ",%20" + country + "%22&format=json")
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
        city = city.replace("%20", " ")
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
        var url: URL? = null
        url = URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20" +
                "woeid%20in%20(SELECT%20woeid%20FROM%20geo.places%20WHERE%20text=%22(" + latitude + "," + longitude + ")%22)%20" +
                "and%20u=%22c%22&format=json")
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
        var url: URL? = null
        url = URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20" +
                "where%20woeid=" + woeid + "%20and%20u=%22c%22&format=json")
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
        var url: URL? = null
        url = URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20" +
                "woeid%20in%20(SELECT%20woeid%20FROM%20geo.places%20WHERE%20text=%22(" + latitude + "," + longitude + ")%22)%20" +
                "and%20u=%22c%22&format=json")
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
}
