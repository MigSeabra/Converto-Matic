package com.rest;

import org.json.JSONObject;

public class CurrencyOps {

    public static double exchangeCurrAtoCurrB (String currA, String currB) throws Exception {

        JSONObject data = DataCall.fetchAllCurrA(currA);

        double currAtoCurrB = data.getJSONObject("rates").getDouble(currB);

        return currAtoCurrB;
    }

}