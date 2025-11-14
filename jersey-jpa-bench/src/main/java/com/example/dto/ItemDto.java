package com.example.dto;

import java.math.BigDecimal;

public class ItemDto {
    public Long id;
    public String sku;
    public String name;
    public BigDecimal price;
    public Integer stock;
    public Long categoryId;
    public String categoryName;
    public String description; // optional heavy field
}
