package com.shyhumangames.app;


import com.shyhumangames.app.javawebserver.configuration.AppConfig;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {   
        int server_port = AppConfig.getJavaWebServerAppConfig().getWebServerPort();
        String server_ip = AppConfig.getJavaWebServerAppConfig().getWebServerIp();
        
        System.out.println( "Runnning JavaWebServer with Configuration server_ip: " + 
                            server_ip + 
                            " server_port: " + server_port);
    }
}
