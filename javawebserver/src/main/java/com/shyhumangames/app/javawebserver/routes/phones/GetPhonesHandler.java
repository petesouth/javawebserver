package com.shyhumangames.app.javawebserver.routes.phones;

import java.io.IOException;
import java.io.OutputStream;

import com.shyhumangames.app.javawebserver.server.HttpHandlerExt;
import com.sun.net.httpserver.HttpExchange;

public class GetPhonesHandler implements HttpHandlerExt {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        
        String response = "{ \"status\": 200, \"message\": \"The Phones Handler\" }";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
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