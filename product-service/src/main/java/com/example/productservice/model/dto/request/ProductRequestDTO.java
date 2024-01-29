package com.example.productservice.model.dto.request;

import lombok.Data;
@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
}

