package com.rest;

import java.util.*;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Controller {

    //Get exchange rate from Currency A to Currency B
    @RequestMapping(value="/exchange", params = {"currA", "currB"}, method=GET)
    public ResponseEntity<Object> generateResponseA(@RequestParam(value="currA") String currA, @RequestParam(value="currB") String currB) throws Exception {

        if(currA.isEmpty() || currB.isEmpty())
            throw new IllegalArgumentException("Missing request parameters");

        JSONObject data = DataCall.fetchAllCurrA(currA);

        Map<String, Double> result = new HashMap<>();
        result.put(currB,data.getJSONObject("rates").getDouble(currB));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get all exchange rates from Currency A
    @RequestMapping(value="/exchange", params = {"currA"}, method=GET)
    public ResponseEntity<Object> generateResponseB(@RequestParam(value="currA") String currA) throws Exception {

        if(currA.isEmpty())
            throw new IllegalArgumentException("Missing request parameters");

        JSONObject data = DataCall.fetchAllCurrA(currA).getJSONObject("rates");

        SortedMap<String, Double> result = new TreeMap<>();

        Set<String> keys = data.keySet();

        for(String key : keys)
            result.put(key, data.getDouble(key));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get value conversion from Currency A to Currency B
    //This endpoint is redundant - use the following one
//    @RequestMapping(value="/convert", params = {"currA", "currB", "value"}, method=GET)
//    public ResponseEntity<Object> generateResponseC(@RequestParam(value="currA") String currA,  @RequestParam(value="currB") String currB, @RequestParam(value="value") double value) throws Exception {
//
//        JSONObject data = DataCall.fetchAllCurrA(currA);
//
//        return new ResponseEntity<Object>("{\"converted\":" + value*data.getJSONObject("rates").getDouble(currB) + "}", HttpStatus.OK);
//    }

    //Get value conversion from Currency A to a list of supplied currencies
    @RequestMapping(value="/convert", method=GET)
    public ResponseEntity<Object> generateResponseD(@RequestParam Map<String, String> customQuery) throws Exception {

        SortedMap<String, Double> result = new TreeMap<>();

        String currA = customQuery.get("currA");
        double value = Double.parseDouble(customQuery.get("value"));

        if(currA == null || currA.isEmpty() || value < 0)
            throw new IllegalArgumentException("Missing request parameters");

        JSONObject data = DataCall.fetchAllCurrA(currA);

        //Only a maximum of 10 currencies are allowed in the request (currA, ... , currK)
        for (char alpha = 'B'; alpha <= 'K'; alpha++) {
            String curr = customQuery.get("curr" + alpha);
            if(curr != null)
                result.put(curr,value*data.getJSONObject("rates").getDouble(curr));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}