package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.Entity;

import java.util.List;

public interface InterfaceRepository <T extends Entity> {

    /**
     * Add an entity to repository.
     * @param entity to add.
     * @throws Exception if the ID already exist.
     */
    void create(T entity) throws Exception;

    /**
     * Creates a list of all entities.
     * @return the list of al entities.
     */
    List<T> readAll();

    /**
     * Get an object.
     *
     * @param id of the object to select.
     * @return the selected object.
     */
    T read(int id);

    /**
     * Updates an existent entity object.
     *
     * @param entity to update.
     * @throws Exception if the id doesn't exist.
     */
    void update(T entity) throws Exception;

    /**
     * Delete an entity object.
     *
     * @param id of the entity to delete.
     * @throws Exception if the id doesn't exist.
     */
    void delete(int id) throws Exception;
}
