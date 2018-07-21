package com.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.BsonDocument;
import org.json.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoCursor;


public class DataCall {

    public static JSONObject fetchAllCurrA(String currA) throws Exception {

        //Check cache (mongodb)
        JSONObject obj = checkCacheData("mongodb://MigSeabra:converto9@ds143971.mlab.com:43971/converto-matic", currA);

        //Document not present in cache (then fetch and insert it)
        if(obj.getString("base").equals("not_found")) {
            obj = performRESTget("http://data.fixer.io/api/latest?access_key=bdbc04ad865f07ba4e6f36a796d5d4b5&base=" + currA + "&symbols=&format=1");
            if(!obj.getBoolean("success")) {
                throw new RuntimeException("Failed fetching currency: server returned an error");
            }
            insertCacheData("mongodb://MigSeabra:converto9@ds143971.mlab.com:43971/converto-matic", obj);
        }

        return obj;
    }

    private static JSONObject checkCacheData(String URL, String currA) {

        MongoClientURI uri  = new MongoClientURI(URL);
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());

        //Database search
        MongoCollection<Document> currency = db.getCollection("currency");
        Document findQuery = new Document("base",currA);
        MongoCursor<Document> cursor = currency.find(findQuery).iterator();

        JSONObject response;

        if(cursor.hasNext()) {
            Document doc = cursor.next();
            response = new JSONObject(doc.toJson());
        } else {
            response = new JSONObject("{\"base\":\"not_found\"}");
        }

        cursor.close();
        client.close();

        return response;
    }

    private static void insertCacheData(String URL, JSONObject data) {

        MongoClientURI uri  = new MongoClientURI(URL);
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> currency = db.getCollection("currency");

        Document datadoc = Document.parse(data.toString()).append("createdAt", new Date());

        currency.createIndex(Indexes.ascending("createdAt"), new IndexOptions().expireAfter(15L, TimeUnit.MINUTES));

        currency.insertOne(datadoc);

        client.close();
    }

    private static JSONObject performRESTget(String URL) throws Exception {

        URL url = new URL(URL);
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
