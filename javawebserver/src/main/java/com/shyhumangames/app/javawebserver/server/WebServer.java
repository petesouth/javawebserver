package com.shyhumangames.app.javawebserver.server;

import java.util.logging.Logger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.IOException;

import com.shyhumangames.app.javawebserver.configuration.AppConfig;


public class WebServer {

   
    private static final int WAIT_SHUTDOWN_TIMEOUT_SECONDS = 60;

    private static final int SERVER_LOOP_DELAY = 10000;

    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());

    private String server_ip;
    private int server_port;
    private int numberOfThreads = AppConfig.DEFAULT_THREAD_POOL_SIZE_NUMBER;
    private HttpServer server;
    private boolean running = false;
    private ExecutorService executorService;

    public WebServer() {
        this.server_ip = AppConfig.DEFAULT_WEB_SERVER_IP;
        this.server_port = AppConfig.DEFAULT_WEB_SERVER_PORT_NUMBER;
        this.numberOfThreads = AppConfig.DEFAULT_THREAD_POOL_SIZE_NUMBER;
    
    }
    
    public WebServer(String server_ip, int server_port) {
        this.server_ip = server_ip;
        this.server_port = server_port;
        this.numberOfThreads = AppConfig.DEFAULT_THREAD_POOL_SIZE_NUMBER;
    }

    public WebServer(String server_ip, int server_port, int numberOfThreads) { 
        this.server_ip = server_ip;
        this.server_port = server_port;
        this.numberOfThreads = numberOfThreads;
    }

    public void run() {
        try {

            
            // Initialize the HTTP server
            InetSocketAddress address = new InetSocketAddress(this.server_ip, this.server_port);
            this.server = HttpServer.create(address, 0);
            
            // Setup the thread pool
            this.executorService = Executors.newFixedThreadPool(this.numberOfThreads);
            this.server.setExecutor(this.executorService);

            // Define contexts and handlers
            this.server.createContext("/", new RouterHandler());
            
            // Add shutdown hook to handle CTRL-C or other interrupt signals.
            this.setupShutdownServerHooks();

            this.running = true;
            server.start();
            WebServer.LOGGER.info("Server is running: ip - " + this.server_ip + " port - " + this.server_port);
            WebServer.LOGGER.info("Server Thread Count - numberOfThreads - " + this.numberOfThreads);

            // Keep the server running
            while (this.running) {
                Thread.sleep(SERVER_LOOP_DELAY);
            }

        } catch (Exception e) {
            WebServer.LOGGER.severe("Error starting server: " + e.getMessage());
        }
    }

    /**
     * Sets up a shutdown hook with the runtime so ctrl-c type activity wont 
     * leave open connections.
     */
    private void setupShutdownServerHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            WebServer.LOGGER.info("Shutdown hook running. Cleaning up resources...");
            server.stop(0);
            this.running = false;
            if (!executorService.isShutdown()) {
                executorService.shutdown(); // Disable new tasks from being submitted
                try {
                    // Wait a while for existing tasks to terminate
                    if (!executorService.awaitTermination(WAIT_SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                        executorService.shutdownNow(); // Cancel currently executing tasks
                        // Wait a while for tasks to respond to being cancelled
                        if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
                            WebServer.LOGGER.severe("ExecutorService did not terminate");
                    }
                } catch (InterruptedException ie) {
                    // (Re-)Cancel if current thread also interrupted
                    executorService.shutdownNow();
                    // Preserve interrupt status
                    Thread.currentThread().interrupt();
                }
            }
        }));
    }

    static class RouterHandler implements HttpHandler {
        public Map<String, HttpHandler> routeMap = new LinkedHashMap<String, HttpHandler>();

        RouterHandler() {
            this.routeMap.put("/test", new TestHandler());
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            HttpHandler handler = this.routeMap.get(path);
            if( handler != null) {
                handler.handle(exchange);
            } else {
                // If yes, handle the request as before
                String response = "{ \"status\": 404, \"message\": \"Not Found\" }";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                    os.close();
                } catch (Throwable e) {
                    WebServer.LOGGER.warning("Error path not found:" + path + " :" + e.getMessage());
                }
                
            }  
            
        }
    }

    static class TestHandler implements HttpHandler {
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{ \"status\": 200, \"message\": \"The Server is up and running\" }";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
