package edu.northeastern.cs5500.starterbot.repository;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.northeastern.cs5500.starterbot.model.Model;
import edu.northeastern.cs5500.starterbot.service.MongoDBService;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.bson.types.ObjectId;

/*
 * This class contains the information for an implementation of the GenericRepository interface
 * utilizing MongoDB for database storage.
 *
 * CRUD and counting operations for a model type are included.
 */
public class MongoDBRepository<T extends Model> implements GenericRepository<T> {

    static final String MONGODB_ID_FIELD = "_id";

    MongoCollection<T> collection;

    /*
     * MongoDB repository injection.
     */
    @Inject
    public MongoDBRepository(Class<T> clazz, MongoDBService mongoDBService) {
        MongoDatabase mongoDatabase = mongoDBService.getMongoDatabase();
        collection = mongoDatabase.getCollection(clazz.getName(), clazz);
    }

    /*
     * Gets an object by its id.
     */
    @Nullable
    public T get(@Nonnull ObjectId id) {
        return collection.find(eq(MONGODB_ID_FIELD, id)).first();
    }

    /*
     * Adds an object by the object.
     */
    @Override
    public T add(@Nonnull T item) {
        if (item.getId() == null) {
            item.setId(new ObjectId());
        }
        collection.insertOne(item);
        return item;
    }
    /*
     * Updates an object by the object.
     */
    @Override
    public T update(@Nonnull T item) {
        return collection.findOneAndReplace(eq(MONGODB_ID_FIELD, item.getId()), item);
    }

    /*
     * Deletes an object by its id.
     */
    @Override
    public void delete(@Nonnull ObjectId id) {
        collection.deleteOne(eq(MONGODB_ID_FIELD, id));
    }

    /*
     * Gets all objects stored in the collection, returns a collection of the objects stored.
     */
    @Override
    public Collection<T> getAll() {
        return collection.find().into(new ArrayList<>());
    }

    /*
     * Counts all objects stored in the collection
     */
    @Override
    public long count() {
        return collection.countDocuments();
    }
}
