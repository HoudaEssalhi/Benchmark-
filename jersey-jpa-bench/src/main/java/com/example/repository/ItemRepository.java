package com.example.repository;

import com.example.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ItemRepository {
    private final EntityManager em;
    public ItemRepository(EntityManager em){ this.em = em; }

    public Item findById(Long id){ return em.find(Item.class, id); }

    public Item findBySku(String sku){
        TypedQuery<Item> q = em.createQuery("select i from Item i where i.sku = :sku", Item.class);
        q.setParameter("sku", sku);
        return q.getResultStream().findFirst().orElse(null);
    }

    public List<Item> findAll(int page, int size){
        TypedQuery<Item> q = em.createQuery("select i from Item i order by i.id", Item.class);
        q.setFirstResult(page * size);
        q.setMaxResults(size);
        return q.getResultList();
    }

    public List<Item> findByCategoryId(Long categoryId, int page, int size){
        TypedQuery<Item> q = em.createQuery("select i from Item i where i.category.id = :cid order by i.id", Item.class);
        q.setParameter("cid", categoryId);
        q.setFirstResult(page * size);
        q.setMaxResults(size);
        return q.getResultList();
    }

    // Anti N+1: fetch category with join (no paging here) — for demo
    public List<Item> findByCategoryIdWithCategoryFetch(Long categoryId){
        TypedQuery<Item> q = em.createQuery("select i from Item i join fetch i.category where i.category.id = :cid", Item.class);
        q.setParameter("cid", categoryId);
        return q.getResultList();
    }

    public Item save(Item i){
        if(i.getId() == null) em.persist(i);
        else i = em.merge(i);
        return i;
    }

    public void delete(Item i){
        em.remove(i);
    }
}
