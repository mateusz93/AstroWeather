package dmcs.astroWeather.util;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class ImageDownloader {

    File downloadImage(String urlAddress) {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
            String filename = "downloadedImage.gif";
            File file = new File(SDCardRoot, filename);
            if (file.createNewFile()) {
                file.createNewFile();
            }
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
            }
            fileOutput.close();
            if (downloadedSize == totalSize) {
                return file;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
