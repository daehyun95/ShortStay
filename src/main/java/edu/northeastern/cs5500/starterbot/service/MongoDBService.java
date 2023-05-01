package edu.northeastern.cs5500.starterbot.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/*
 * MongoDBService, a service that uses the Service public interface.
 */
@Singleton
@Slf4j
public class MongoDBService implements Service {

    /*
     * Getter for the MongoDB database URI, returns MongoDB URI, a String.
     */
    static String getDatabaseURI() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        final String databaseURI = processBuilder.environment().get("MONGODB_URI");
        if (databaseURI != null) {
            return databaseURI;
        }
        return "mongodb://localhost:27017/studentsubletdatabase"; // connect to localhost by default
    }

    /*
     * Getter for the mongoDatabase
     */
    @Getter private MongoDatabase mongoDatabase;

    /*
     * MongoDB service, a method that sets up the monogDB service for Dagger
     */
    @Inject
    public MongoDBService() {
        CodecRegistry codecRegistry =
                fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        ConnectionString connectionString = new ConnectionString(getDatabaseURI());

        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder()
                        .codecRegistry(codecRegistry)
                        .applyConnectionString(connectionString)
                        .build();

        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        mongoDatabase = mongoClient.getDatabase(connectionString.getDatabase());
    }

    /*
     * Logs event for MonogDB service
     */
    @Override
    public void register() {
        log.info("MongoDBService > register");
    }
}
