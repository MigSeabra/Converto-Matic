package com.rest;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Controller {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    //Get exchange rate from Currency A to Currency B
    @RequestMapping(value="/exchange", params = {"currA", "currB"}, method=GET)
    public Response generateResponseA(@RequestParam(value="currA") String currA, @RequestParam(value="currB") String currB) throws Exception {

        CurrencyOps.exchangeCurrAtoCurrB(currA, currB);

        return new Response(counter.incrementAndGet(),
                String.format(template, currB));
    }

    //Get all exchange rates from Currency A
    @RequestMapping(value="/exchange", params = {"currA"}, method=GET)
    public Response generateResponseB(@RequestParam(value="currA") String currA) throws Exception {

        CurrencyOps.exchangeAllCurrA(currA);

        return new Response(counter.incrementAndGet(),
                String.format(template, currA));
    }

    //Get value conversion from Currency A to Currency B
    @RequestMapping(value="/convert", params = {"currA", "currB", "value"}, method=GET)
    public Response generateResponseC(@RequestParam(value="currA") String currA,  @RequestParam(value="currB") String currB, @RequestParam(value="value") double value) throws Exception {

        CurrencyOps.convertCurrAtoCurrB(currA, currB, value);

        return new Response(counter.incrementAndGet(),
                String.format(template, value));
    }

    /*//Get value conversion from Currency A to a list of supplied currencies
    @RequestMapping(value="/convert", params = {"currA", "currB", "value"}, method=GET)
    public Response generateResponseD(@RequestParam(value="name", defaultValue="World") String name) {
        return new Response(counter.incrementAndGet(),
                String.format(template, name));
    }*/
}