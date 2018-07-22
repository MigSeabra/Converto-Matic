package com.rest;

import org.json.*;


public class DataCall {

    public static JSONObject fetchAllCurrA(String currA) throws Exception {

        //Check cache (mongodb)
        JSONObject obj = CacheOps.checkCacheData("mongodb://MigSeabra:converto9@ds143971.mlab.com:43971/converto-matic", currA);

        //Document not present in cache (then fetch and insert it)
        if(obj.getString("base").equals("not_found")) {
            obj = HttpOps.performRESTget("http://data.fixer.io/api/latest?access_key=bdbc04ad865f07ba4e6f36a796d5d4b5&base=" + currA + "&symbols=&format=1");
            if(!obj.getBoolean("success")) {
                throw new RuntimeException("Failed fetching currency: server returned an error");
            }
            CacheOps.insertCacheData("mongodb://MigSeabra:converto9@ds143971.mlab.com:43971/converto-matic", obj);
        }

        return obj;
    }

}
