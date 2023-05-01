package edu.northeastern.cs5500.starterbot.repository;

import edu.northeastern.cs5500.starterbot.model.Model;
import java.util.Collection;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bson.types.ObjectId;

/*
 * In memory repostiory, a public Singleton
 * for locally storing information in a hashmap.
 */
@Singleton
public class InMemoryRepository<T extends Model> implements GenericRepository<T> {

    /*
     * Hashmap, the local data collection.
     */
    HashMap<ObjectId, T> collection;

    /*
     * At start, injects new hashmap.
     */
    @Inject
    public InMemoryRepository() {
        collection = new HashMap<>();
    }

    /*
     * Gets object by id.
     */
    @Nullable
    public T get(@Nonnull ObjectId id) {
        return collection.get(id);
    }

    /*
     * Adds object by object.
     */
    @Override
    public T add(@Nonnull T item) {
        ObjectId id = item.getId();
        if (id == null) {
            id = new ObjectId();
            item.setId(id);
        }
        collection.put(id, item);
        return item;
    }

    /*
     * Updates object by object.
     */
    @Override
    public T update(@Nonnull T item) {
        collection.put(item.getId(), item);
        return item;
    }

    /*
     * Deletes object by id.
     */
    @Override
    public void delete(@Nonnull ObjectId id) {
        collection.remove(id);
    }

    /*
     * Returns a collection of all values in local inmemoryrepository hashmap.
     */
    @Override
    public Collection<T> getAll() {
        return collection.values();
    }

    /*
     * Returns number of keys in the collection.
     */
    @Override
    public long count() {
        return collection.size();
    }
}
