package com.shyhumangames.app.javawebserver.database.mongo;

import com.mongodb.client.MongoClients;

import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.shyhumangames.app.javawebserver.configuration.AppConfig;
import com.mongodb.client.MongoCollection;


public class MongoDBService {
    
    private static MongoDBService mongoDBService = null;
    private static final Logger LOGGER = Logger.getLogger(MongoDBService.class.getName());
    private MongoClient mongoClient = null;
    private MongoDatabase mongoDB = null;

    public static MongoDBService getMongoDBService() {
        if (mongoDBService == null) {
            mongoDBService = new MongoDBService();
            mongoDBService.init();
        }
        return mongoDBService;
    }

    private MongoDBService() {
    }

    public MongoDatabase getMongoDB() {
        return this.mongoDB;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return this.mongoDB.getCollection(collectionName);
    }

    public void init() {
        String mongoConnect = AppConfig.getAppConfig().getMongoConnect();
        String mongoDbName = AppConfig.getAppConfig().getMongoDBName();

        MongoDBService.LOGGER.info("mongoConnect: " + mongoConnect + " mongoDbName: " + mongoDbName);
        
        this.mongoClient = MongoClients.create(mongoConnect);
        this.mongoDB = this.mongoClient.getDatabase(mongoDbName);

        // Adding a shutdown hook to close the MongoClient when the application stops
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (this.mongoClient != null) {
                this.mongoClient.close();
                LOGGER.info("MongoDB client closed");
            }
        }));
    }

    public void close() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
            LOGGER.info("MongoDB client closed");
        }
    }
}

