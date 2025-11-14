package com.example.repository;

import com.example.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CategoryRepository {
    private final EntityManager em;
    public CategoryRepository(EntityManager em){ this.em = em; }

    public Category findById(Long id){ return em.find(Category.class, id); }

    public Category findByCode(String code){
        TypedQuery<Category> q = em.createQuery("select c from Category c where c.code = :code", Category.class);
        q.setParameter("code", code);
        List<Category> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }

    public List<Category> findAll(int page, int size){
        TypedQuery<Category> q = em.createQuery("select c from Category c order by c.id", Category.class);
        q.setFirstResult(page * size);
        q.setMaxResults(size);
        return q.getResultList();
    }

    public Category save(Category c){
        if(c.getId() == null) em.persist(c);
        else c = em.merge(c);
        return c;
    }

    public void delete(Category c){
        em.remove(c);
    }
}
