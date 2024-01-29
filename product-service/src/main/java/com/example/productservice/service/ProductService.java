package com.example.productservice.service;



import com.example.productservice.model.dto.request.ProductRequestDTO;
import com.example.productservice.model.dto.response.ProductResponseDTO;
import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
    void deleteProduct(Long id);
}

