package com.example.productservice.service.impl;

import com.example.productservice.model.dto.request.ProductRequestDTO;
import com.example.productservice.model.dto.response.ProductResponseDTO;
import com.example.productservice.model.entity.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(product -> modelMapper.map(product, ProductResponseDTO.class)).orElse(null);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product newProduct = modelMapper.map(productRequestDTO, Product.class);
        Product savedProduct = productRepository.save(newProduct);
        return modelMapper.map(savedProduct, ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            modelMapper.map(productRequestDTO, existingProduct);
            Product updatedProduct = productRepository.save(existingProduct);
            return modelMapper.map(updatedProduct, ProductResponseDTO.class);
        }

        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
