package com.example.productservice.model.dto.response;

import lombok.Data;
@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
}

