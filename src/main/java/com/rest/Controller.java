package com.rest;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Controller {

    //Get exchange rate from Currency A to Currency B
    @RequestMapping(value="/exchange", params = {"currA", "currB"}, method=GET)
    public Response generateResponseA(@RequestParam(value="currA") String currA, @RequestParam(value="currB") String currB) throws Exception {

        JSONObject data = DataCall.fetchAllCurrA(currA);

        double num = data.getJSONObject("rates").getDouble(currB);


        return new Response(0, "asd");
    }

    //Get all exchange rates from Currency A
    @RequestMapping(value="/exchange", params = {"currA"}, method=GET)
    public Response generateResponseB(@RequestParam(value="currA") String currA) throws Exception {

        JSONObject data = DataCall.fetchAllCurrA(currA);

        String asd = data.getJSONObject("rates").toString();

        return new Response(0, "asd");
    }

    //Get value conversion from Currency A to Currency B
    @RequestMapping(value="/convert", params = {"currA", "currB", "value"}, method=GET)
    public Response generateResponseC(@RequestParam(value="currA") String currA,  @RequestParam(value="currB") String currB, @RequestParam(value="value") double value) throws Exception {

        JSONObject data = DataCall.fetchAllCurrA(currA);

        double asd = value*data.getJSONObject("rates").getDouble(currB);

        return new Response(0, "asd");
    }

    //Get value conversion from Currency A to a list of supplied currencies
    @RequestMapping(value="/convert", params = {"currA", "currB", "value"}, method=GET)
    public Response generateResponseD(@RequestParam(value="currA") String currA) {
        return new Response(0, "asd");
    }
}