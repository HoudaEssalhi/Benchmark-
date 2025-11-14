package com.example.service;


import com.example.config.JpaUtil;
import com.example.dto.CategoryDto;
import com.example.entity.Category;
import com.example.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {
    public List<CategoryDto> list(int page, int size){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            CategoryRepository repo = new CategoryRepository(em);
            List<Category> cats = repo.findAll(page, size);
            return cats.stream().map(this::toDto).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    public CategoryDto find(Long id){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            CategoryRepository repo = new CategoryRepository(em);
            Category c = repo.findById(id);
            return c == null ? null : toDto(c);
        } finally { em.close(); }
    }

    public CategoryDto create(CategoryDto dto){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CategoryRepository repo = new CategoryRepository(em);
            Category c = new Category();
            c.setCode(dto.code);
            c.setName(dto.name);
            repo.save(c);
            tx.commit();
            return toDto(c);
        } catch (Exception e){
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    public CategoryDto update(Long id, CategoryDto dto){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CategoryRepository repo = new CategoryRepository(em);
            Category c = repo.findById(id);
            if(c == null){ tx.rollback(); return null; }
            c.setName(dto.name);
            c.setCode(dto.code);
            repo.save(c);
            tx.commit();
            return toDto(c);
        } catch (Exception e) {
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    public boolean delete(Long id){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CategoryRepository repo = new CategoryRepository(em);
            Category c = repo.findById(id);
            if(c == null){ tx.rollback(); return false; }
            // optional: check child items constraint
            repo.delete(c);
            tx.commit();
            return true;
        } catch (Exception e){
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    private CategoryDto toDto(Category c){
        CategoryDto d = new CategoryDto();
        d.id = c.getId();
        d.code = c.getCode();
        d.name = c.getName();
        return d;
    }
}
