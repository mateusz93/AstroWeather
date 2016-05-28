package dmcs.astroWeather.util;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mateusz on 2016-05-27.
 */
public class WeatherDownloader {

    public static String getWoeidByCityAndCountry(String city, String country) {
        StringBuilder result = new StringBuilder();
        URL url = null;
        try {
            url = new URL("https://query.yahooapis.com/v1/public/yql?q=" +
                    "select%20*%20from%20geo.places(1)%20where%20text=%22" + city +
                    ",%20" + country + "%22&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            JSONObject json = new JSONObject(result.toString());
            return json.getJSONObject("query").getJSONObject("results").getJSONObject("place").getString("woeid");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
