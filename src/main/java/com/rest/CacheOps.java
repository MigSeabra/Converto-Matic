package com.rest;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;

import java.util.Date;

public class CacheOps {

    //Check if document is present in cache - mongodb
    public static JSONObject checkCacheData(String URL, String currA) {

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

    //Insert document in cache - mongodb
    public static void insertCacheData(String URL, JSONObject data) {

        MongoClientURI uri  = new MongoClientURI(URL);
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());

        MongoCollection<Document> currency = db.getCollection("currency");

        //TTL index
        Document datadoc = Document.parse(data.toString()).append("createdAt", new Date());

        //the below index was created once on the database
        //currency.createIndex(Indexes.ascending("createdAt"), new IndexOptions().expireAfter(1L, TimeUnit.MINUTES));

        currency.insertOne(datadoc);

        client.close();
    }



}
