package com.example.service;

import com.example.config.JpaUtil;
import com.example.dto.ItemDto;
import com.example.entity.Category;
import com.example.entity.Item;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.stream.Collectors;

public class ItemService {
    public List<ItemDto> list(int page, int size){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            ItemRepository repo = new ItemRepository(em);
            return repo.findAll(page,size).stream().map(this::toDto).collect(Collectors.toList());
        } finally { em.close(); }
    }

    public ItemDto find(Long id){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            ItemRepository repo = new ItemRepository(em);
            Item i = repo.findById(id);
            return i == null ? null : toDto(i);
        } finally { em.close(); }
    }

    public List<ItemDto> findByCategory(Long categoryId, int page, int size, boolean joinFetch){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            ItemRepository repo = new ItemRepository(em);
            List<Item> items = joinFetch ? repo.findByCategoryIdWithCategoryFetch(categoryId) : repo.findByCategoryId(categoryId, page, size);
            return items.stream().map(this::toDto).collect(Collectors.toList());
        } finally { em.close(); }
    }

    public ItemDto create(ItemDto dto){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CategoryRepository crepo = new CategoryRepository(em);
            Category c = crepo.findById(dto.categoryId);
            if(c == null) throw new IllegalArgumentException("Category not found");
            Item i = new Item();
            i.setSku(dto.sku);
            i.setName(dto.name);
            i.setPrice(dto.price);
            i.setStock(dto.stock);
            i.setDescription(dto.description);
            i.setCategory(c);
            ItemRepository repo = new ItemRepository(em);
            repo.save(i);
            tx.commit();
            return toDto(i);
        } catch (Exception e){
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    public ItemDto update(Long id, ItemDto dto){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ItemRepository repo = new ItemRepository(em);
            Item i = repo.findById(id);
            if(i == null){ tx.rollback(); return null; }
            i.setName(dto.name);
            i.setPrice(dto.price);
            i.setStock(dto.stock);
            i.setDescription(dto.description);
            // change category?
            if(dto.categoryId != null && !dto.categoryId.equals(i.getCategory().getId())){
                CategoryRepository crepo = new CategoryRepository(em);
                Category newc = crepo.findById(dto.categoryId);
                if(newc == null) throw new IllegalArgumentException("Category not found");
                i.setCategory(newc);
            }
            repo.save(i);
            tx.commit();
            return toDto(i);
        } catch (Exception e){
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    public boolean delete(Long id){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ItemRepository repo = new ItemRepository(em);
            Item i = repo.findById(id);
            if(i == null){ tx.rollback(); return false; }
            repo.delete(i);
            tx.commit();
            return true;
        } catch (Exception e){
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    private ItemDto toDto(Item i){
        ItemDto d = new ItemDto();
        d.id = i.getId();
        d.sku = i.getSku();
        d.name = i.getName();
        d.price = i.getPrice();
        d.stock = i.getStock();
        d.categoryId = i.getCategory() != null ? i.getCategory().getId() : null;
        d.categoryName = i.getCategory() != null ? i.getCategory().getName() : null;
        d.description = i.getDescription();
        return d;
    }
}
