package com.example.internetworking;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLParser {

    public String getXmlFromUrl(String strUrl) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);

            int code = conn.getResponseCode();
            InputStream is = (code >= 400) ? conn.getErrorStream() : conn.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();
        } catch (Exception e) {
            Log.e("XMLParser", "Error: " + e.getMessage());
        } finally {
            if (conn != null) conn.disconnect();
        }
        return sb.toString();
    }
}