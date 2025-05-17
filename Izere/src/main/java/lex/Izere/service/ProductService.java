package lex.Izere.service;

import lex.Izere.dto.ProductDTO;
import lex.Izere.exception.ResourceNotFoundException;
import lex.Izere.model.Product;
import lex.Izere.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductByCode(String code) {
        return productRepository.findById(code)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + code));
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setProductType(productDTO.getProductType());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        
        return convertToDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO updateProduct(String code, ProductDTO productDTO) {
        Product product = productRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + code));

        product.setName(productDTO.getName());
        product.setProductType(productDTO.getProductType());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());

        return convertToDTO(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(String code) {
        if (!productRepository.existsById(code)) {
            throw new ResourceNotFoundException("Product not found with code: " + code);
        }
        productRepository.deleteById(code);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setCode(product.getCode());
        dto.setName(product.getName());
        dto.setProductType(product.getProductType());
        dto.setPrice(product.getPrice());
        dto.setInDate(product.getInDate());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }
} 