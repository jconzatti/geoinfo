package com.geoinfo.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class Repository<K,V> {
    protected EntityManager manager;
    private final Class<K> classEntity;

    public Repository(EntityManager manager, Class<K> classEntity) {
        this.manager = manager;
        this.classEntity = classEntity;
    }
    
    public K find(V v){
        return this.manager.find(classEntity, v);
    }
    
    public K getReference(V v){
        return this.manager.getReference(classEntity, v);
    }
    
    public void insert(K k){
        this.manager.persist(k);
    }
    
    public K edit(K k){
        return this.manager.merge(k);
    }
    
    public void delete(V v){
        K k = this.find(v);
        this.manager.remove(v);
    }
    
    public List<K> getList(){
        Query query = this.manager.createQuery("select x from " + getClassEntityName() + " x");
        return query.getResultList();
    }
    
    public String getClassEntityName(){
        return this.classEntity.getSimpleName();
    }
    
}
