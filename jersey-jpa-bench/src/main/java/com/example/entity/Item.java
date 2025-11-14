package com.example.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "item")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=64)
    private String sku;

    @Column(nullable=false, length=128)
    private String name;

    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal price;

    @Column(nullable=false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @Column(name="updated_at", nullable=false)
    private Instant updatedAt = Instant.now();

    @Column(length = 4096)
    private String description; // heavy payload field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // getters & setters
}
