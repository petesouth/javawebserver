package com.shyhumangames.app.javawebserver.routes.phones;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.shyhumangames.app.javawebserver.database.mongo.MongoDBService;
import com.shyhumangames.app.javawebserver.server.HttpHandlerExt;
import com.sun.net.httpserver.HttpExchange;

public class GetPhonesHandler implements HttpHandlerExt {

    private static final Logger LOGGER = Logger.getLogger(GetPhonesHandler.class.getName());

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();
        String[] paths = path.split(("/"));
        int _id = Integer.parseInt(paths[2]);
        MongoCollection<Document> collection = MongoDBService.getMongoDBService().getCollection("phones");
        Bson filter = Filters.eq("_id", _id);

        exchange.getResponseHeaders().set("Content-Type", "application/json");

        // Find the first document that matches the filter.
        // If expecting multiple results, use `find(filter).iterator()` and handle
        // accordingly.
        Document phone = collection.find(filter).first();
        OutputStream os = exchange.getResponseBody();
        String response = "{ \"status\": 404, \"message\": \"Phone not found\" }";
                 
        if (phone != null) {
            LOGGER.info("Ticket found with _id: " + _id + " values ticket: " + phone.toJson());
            response = phone.toJson();
            exchange.sendResponseHeaders(200, response.length());
        } else {
            LOGGER.info("No Phone found with _id: " + _id);
            exchange.sendResponseHeaders(404, response.length());
        }
        
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public void init() {

    }

    @Override
    public void shutdown() {

    }
}