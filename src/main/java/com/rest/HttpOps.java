package com.rest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpOps {

    public static JSONObject performRESTget(String URL) throws Exception {

        java.net.URL url = new URL(URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        int responseCode = conn.getResponseCode();

        //Request error processing
        if(responseCode != HttpURLConnection.HTTP_OK)
            throw new RuntimeException("Failed fetching currency : HTTP error code : " + responseCode);

        //Request processing
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

//      conn.disconnect();

        String line, output="";
        while ((line = br.readLine()) != null)
            output = output.concat(line + "\n");

        return new JSONObject(output);
    }



}
