package com.rest;

import org.json.JSONObject;

public class CurrencyOps {

    public static double exchangeCurrAtoCurrB(String currA, String currB) throws Exception {

        JSONObject data = DataCall.fetchAllCurrA(currA);

        return data.getJSONObject("rates").getDouble(currB);
    }

    public static String exchangeAllCurrA(String currA) throws Exception {

        JSONObject data = DataCall.fetchAllCurrA(currA);

        return data.getJSONObject("rates").toString();
    }

    public static double convertCurrAtoCurrB(String currA, String currB, double value) throws Exception {

        JSONObject data = DataCall.fetchAllCurrA(currA);

        return value*data.getJSONObject("rates").getDouble(currB);
    }

}