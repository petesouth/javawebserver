package com.shyhumangames.app;


import java.io.IOException;
import java.util.logging.Logger;

import com.shyhumangames.app.javawebserver.configuration.AppConfig;
import com.shyhumangames.app.javawebserver.routes.ticket.GetTicketsHandler;
import com.shyhumangames.app.javawebserver.routes.phones.GetPhonesHandler;
import com.shyhumangames.app.javawebserver.routes.players.GetPlayersHandler;
import com.shyhumangames.app.javawebserver.server.Middleware;
import com.shyhumangames.app.javawebserver.server.WebServer;
import com.sun.net.httpserver.HttpExchange;

/**
 * Runs the JavaWebServer WebServer with configured Routes
 *
 */
public class App 
{
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    
    public static void main( String[] args )
    {   
        try {
            int server_port = AppConfig.getAppConfig().getWebServerPort();
            String server_ip = AppConfig.getAppConfig().getWebServerIp();
            int numberOfThreads = AppConfig.getAppConfig().getNumberOfThreads();

            WebServer webServer = new WebServer(server_ip, server_port, numberOfThreads); 
            
            // Add whatever middleware you'd like
            // In this case Ill log post and pre for /patients* url
            webServer.addMiddleware(".*", new Middleware() {

                @Override
                public void preHandle(HttpExchange exchange) throws IOException {
                    App.LOGGER.info("\n****PRE_HANDLE GLOBAL handle middleware executing for" + 
                                    exchange.getRequestURI().getPath());
                }

                @Override
                public void postHandle(HttpExchange exchange) throws IOException {
                    App.LOGGER.info("\n****POST_HANDLE GLOBAL handle middleware executing for" + 
                                    exchange.getRequestURI().getPath());
                }
                
            });

            webServer.addMiddleware("^/players/\\d+$", new Middleware() {

                @Override
                public void preHandle(HttpExchange exchange) throws IOException {
                    App.LOGGER.info(("=====PRE_HANDLE Players handle middleware executing for" + exchange.getRequestURI().getPath()));
                }

                @Override
                public void postHandle(HttpExchange exchange) throws IOException {
                    App.LOGGER.info(("=====POST_HANDLE Players handle middleware executing for" + exchange.getRequestURI().getPath()));
                }
                
            });

           // SET UP WHATEVER ROUTES: 
            webServer.addRoute("/tickets/${id}", new GetTicketsHandler());
            webServer.addRoute("/players/${id}", new GetPlayersHandler());
            webServer.addRoute("/phones/${id}", new GetPhonesHandler());

            webServer.run();
    
        } catch(Throwable t) {
            App.LOGGER.warning("Error occured when trying to run the WebServer: " + 
                                t.getClass().getName() + 
                                " error: " + 
                                t.getMessage());    
        }
        
        App.LOGGER.info( "Good Bye");
    }
}
