package com.shyhumangames.app.javawebserver.server;



public interface Middleware {
    void preHandle(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException;
    void postHandle(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException;
}