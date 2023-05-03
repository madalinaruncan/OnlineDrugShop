package com.ubb.postuniv.repository;

import com.ubb.postuniv.domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository <T extends Entity> implements InterfaceRepository<T>{
    private Map<Integer, T> memoryInfo;
    public InMemoryRepository() {
        this.memoryInfo = new HashMap<>();
    }

    @Override
    public void create(T entity) throws Exception {
        if (this.memoryInfo.containsKey(entity.getId())) {
            throw new Exception("There is already an entity with the id: " + entity.getId());
        }
        this.memoryInfo.put(entity.getId(), entity);
    }

    @Override
    public List<T> readAll() {
        return new ArrayList<>(this.memoryInfo.values());
    }

    @Override
    public T read(int id) {
        return this.memoryInfo.get(id);
    }

    @Override
    public void update(T entity) throws Exception {
        if (!this.memoryInfo.containsKey(entity.getId())) {
            throw new Exception("There is no entity with the id: " + entity.getId() + " to update.");
        }
        this.memoryInfo.put(entity.getId(), entity);
    }

    @Override
    public void delete(int id) throws Exception {
        if (!this.memoryInfo.containsKey(id)) {
            throw new Exception("There is no entity with the id: " + id + " to delete.");
        }
        this.memoryInfo.remove(id);
    }
}
