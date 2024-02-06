package com.shyhumangames.app.javawebserver.routes.players;

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

public class GetPlayersHandler implements HttpHandlerExt {

    private static final Logger LOGGER = Logger.getLogger(GetPlayersHandler.class.getName());

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();
        String[] paths = path.split(("/"));
        int _id = Integer.parseInt(paths[2]);
        MongoCollection<Document> collection = MongoDBService.getMongoDBService().getCollection("players");
        Bson filter = Filters.eq("_id", _id);

        exchange.getResponseHeaders().set("Content-Type", "application/json");

        // Find the first document that matches the filter.
        // If expecting multiple results, use `find(filter).iterator()` and handle
        // accordingly.
        Document player = collection.find(filter).first();
        OutputStream os = exchange.getResponseBody();
        String response = "{ \"status\": 404, \"message\": \"Player not found\" }";
                 
        if (player != null) {
            LOGGER.info("Player found with _id: " + _id + " values player: " + player.toJson());
            response = player.toJson();
            exchange.sendResponseHeaders(200, response.length());
        } else {
            LOGGER.info("No player found with _id: " + _id);
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