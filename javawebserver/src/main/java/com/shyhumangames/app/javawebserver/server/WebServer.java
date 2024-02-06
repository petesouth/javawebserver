package com.shyhumangames.app.javawebserver.server;

import java.util.logging.Logger;
import java.util.logging.Level;

public class WebServer {
    
    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());
    

    private String server_ip;
    private int server_port;
    private boolean running = false;
    public WebServer(String server_ip, int server_port) {
        this.server_ip = server_ip;
        this.server_port = server_port;
    }
    

    public void run() {
        // A flag to control the loop. It's volatile to ensure visibility between threads.
        this.running = true;

        // Add shutdown hook to handle CTRL-C or other interrupt signals.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            WebServer.LOGGER.info("Shutdown hook running. Cleaning up resources...");
            this.running = false; // Update the flag to stop the loop.
        }));
        
        WebServer.LOGGER.info("Running server: ip - " + this.server_ip + " port - " + this.server_port);

        // Main loop
        while (this.running) {
            try {
                // Simulate some work with sleep
                Thread.sleep(1000); // or use some blocking operation here
                System.out.print(".");
                
            } catch (InterruptedException e) {
                // Restore the interrupted status
                Thread.currentThread().interrupt();
                // Optional: Log the interruption or handle it according to your application's needs.
                WebServer.LOGGER.info("Thread was interrupted, stopping...");
                break;
            }
            // Perform your tasks here...
        }
    }
    
    
}
