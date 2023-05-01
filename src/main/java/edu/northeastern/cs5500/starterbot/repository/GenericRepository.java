package edu.northeastern.cs5500.starterbot.repository;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.bson.types.ObjectId;

/*
 * Generic repository is aan interface containing the methods
 * required of a repository such as:
 * CRUD operactions, getall, and count.
 */
public interface GenericRepository<T> {
    /*
     * Gets an object by its ID.
     */
    public T get(@Nonnull ObjectId id);

    /*
     * Adds an object by the object.
     */
    public T add(@Nonnull T item);

    /*
     * Updates an object by the object
     */
    public T update(@Nonnull T item);

    /*
     * Deletes an object by its ID.
     */
    public void delete(@Nonnull ObjectId id);

    /*
     * Returns the collection
     */
    public Collection<T> getAll();

    /*
     * Counts items in the collection
     */
    public long count();
}
