package com.shyhumangames.app;


import java.util.logging.Logger;

import com.shyhumangames.app.javawebserver.configuration.AppConfig;
import com.shyhumangames.app.javawebserver.routes.ticket.GetTicketsHandler;
import com.shyhumangames.app.javawebserver.routes.phones.GetPhonesHandler;
import com.shyhumangames.app.javawebserver.routes.players.GetPlayersHandler;
import com.shyhumangames.app.javawebserver.server.WebServer;

/**
 * Hello world!
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
            
            // TODO: SET UP WHATEVER ROUTES: 
            webServer.addRoute("/tickets/${id}", new GetTicketsHandler());
            webServer.addRoute("/players/${id}", new GetPlayersHandler());
            webServer.addRoute("/players/${id}/phones", new GetPhonesHandler());

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
