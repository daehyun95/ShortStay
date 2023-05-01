package edu.northeastern.cs5500.starterbot.model;

import org.bson.types.ObjectId;

/*
 * Model interface that contains an id for an object.
 */
public interface Model {
    /*
     * Getter for the Model object id.
     * Returns id, an ObjectId.
     */
    ObjectId getId();

    /*
     * Setter for the Model object id.
     * Requires an id input, an ObjectId.
     */
    void setId(ObjectId id);
}
