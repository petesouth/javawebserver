package com.shyhumangames.app.javawebserver.configuration;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;


public class AppConfig {

    private static final Logger LOGGER = Logger.getLogger(AppConfig.class.getName());
    

    public static final String LOGGING_LEVEL = "logging_level";
    public static final String WEB_SERVER_IP = "web_server_ip";
    public static final String WEB_SERVER_PORT = "web_server_port";
    public static final String NUMBER_OF_THREADS_PARAM = "numberOfThreads";
    public static final String DEFAULT_WEB_SERVER_IP = "0.0.0.0";
    public static final int DEFAULT_WEB_SERVER_PORT_NUMBER = 8080;
    public static final int DEFAULT_THREAD_POOL_SIZE_NUMBER = 10;

    public static final String JAVAWEBSERVER_PROPERTIES = "javawebserver.properties";
    public static AppConfig appConfig = null;

    public static AppConfig getAppConfig() {
        if( AppConfig.appConfig == null) {
            AppConfig.appConfig = new AppConfig(AppConfig.JAVAWEBSERVER_PROPERTIES);
        }

        return AppConfig.appConfig;
    }


    private Properties properties;

    public AppConfig(String configFilePath) {
        properties = new Properties();
        loadProperties(configFilePath);
    }

    private void loadProperties(String configFileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IOException("Unable to find " + configFileName);
            }
            // Load the properties file
            properties.load(input);
        } catch (IOException ex) {
            AppConfig.LOGGER.warning("Error occurred loading config file: " + 
                                     configFileName + 
                                     ". Ensure the file is in the classpath.");
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int getNumberOfThreads() throws NumberFormatException {
        String numberOfThreadsStr = this.getProperty(NUMBER_OF_THREADS_PARAM);
        if( numberOfThreadsStr == null || numberOfThreadsStr.length() < 1 ) {
            numberOfThreadsStr = "" + AppConfig.DEFAULT_THREAD_POOL_SIZE_NUMBER;
            this.properties.setProperty(NUMBER_OF_THREADS_PARAM, numberOfThreadsStr);
        }
        return Integer.valueOf(numberOfThreadsStr).intValue();
    }

    public int getWebServerPort() throws NumberFormatException {
        String portStr = this.getProperty(WEB_SERVER_PORT);
        if( portStr == null || portStr.length() < 1 ) {
            portStr = "" + AppConfig.DEFAULT_WEB_SERVER_PORT_NUMBER;
            this.properties.setProperty(WEB_SERVER_PORT, portStr);
        }
        return Integer.valueOf(portStr).intValue();
    }

    public String getWebServerIp() {
        String serverIp = this.getProperty(WEB_SERVER_IP);
        if( serverIp == null || serverIp.length() < 1 ) {
            serverIp = AppConfig.DEFAULT_WEB_SERVER_IP;
            this.properties.setProperty(WEB_SERVER_IP, serverIp);
            AppConfig.LOGGER.info(AppConfig.DEFAULT_WEB_SERVER_IP + 
                                  " was not defined using the default:" + 
                                  serverIp);
        }
        return serverIp;
    }


    
}

