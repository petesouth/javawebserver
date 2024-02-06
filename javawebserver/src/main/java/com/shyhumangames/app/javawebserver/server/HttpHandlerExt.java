package com.shyhumangames.app.javawebserver.server;

import com.sun.net.httpserver.HttpHandler;

/**
 * This is so I can snap in HttpHandlers as routes that have 
 * Resources like JDBC etc. etc.
 */
public interface HttpHandlerExt extends HttpHandler {
    
    void init();
    void shutdown();
}
