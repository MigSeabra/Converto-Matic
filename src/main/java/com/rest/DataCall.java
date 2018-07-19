package com.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import org.json.*;


public class DataCall {

    //Returns all available exchange rates from Currency A and stores them in Mongodb
    public static JSONObject fetchAllCurrA(String currA) throws Exception {



        URL url = new URL("http://data.fixer.io/api/latest?access_key=bdbc04ad865f07ba4e6f36a796d5d4b5&base=" + currA + "&symbols=&format=1");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        //Request sending
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed fetching currency : HTTP error code : " + conn.getResponseCode());
        }

        //Request processing
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String line, output="";
        while ((line = br.readLine()) != null)
            output = output.concat(line + "\n");

        JSONObject obj = new JSONObject(output);

        if(!obj.getBoolean("success")) {
            throw new RuntimeException("Failed fetching currency: server returned an error");
        }

        conn.disconnect();

        return obj;
    }

    private void storeInMongoDB() {



    }

}
