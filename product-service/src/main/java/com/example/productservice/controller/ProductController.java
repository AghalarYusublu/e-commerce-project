package com.example.productservice.controller;
import com.example.productservice.model.dto.request.ProductRequestDTO;
import com.example.productservice.model.dto.response.ProductResponseDTO;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.updateProduct(id, productRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}

