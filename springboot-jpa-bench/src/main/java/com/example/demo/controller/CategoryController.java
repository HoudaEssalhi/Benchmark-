package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Category category = service.findById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.ok(service.save(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
