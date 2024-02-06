module com.shyhumangames.app {
    requires java.logging;
    requires jdk.httpserver;
    requires com.google.gson;
    
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;

    exports com.shyhumangames.app;
}
