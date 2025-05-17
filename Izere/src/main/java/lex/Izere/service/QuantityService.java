package lex.Izere.service;

import lex.Izere.dto.QuantityDTO;
import lex.Izere.exception.BusinessException;
import lex.Izere.exception.ResourceNotFoundException;
import lex.Izere.model.Product;
import lex.Izere.model.Quantity;
import lex.Izere.repository.ProductRepository;
import lex.Izere.repository.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuantityService {
    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<QuantityDTO> getQuantitiesByProduct(String productCode) {
        return quantityRepository.findByProductCode(productCode)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuantityDTO addQuantity(QuantityDTO quantityDTO) {
        Product product = productRepository.findById(quantityDTO.getProductCode())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Quantity quantity = new Quantity();
        quantity.setProduct(product);
        quantity.setQuantity(quantityDTO.getQuantity());
        quantity.setOperation(Quantity.OperationType.IN);
        quantity.setDate(LocalDateTime.now());

        return convertToDTO(quantityRepository.save(quantity));
    }

    @Transactional
    public QuantityDTO removeQuantity(QuantityDTO quantityDTO) {
        Product product = productRepository.findById(quantityDTO.getProductCode())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        int availableQuantity = getAvailableQuantity(product.getCode());
        if (availableQuantity < quantityDTO.getQuantity()) {
            throw new BusinessException("Insufficient quantity available");
        }

        Quantity quantity = new Quantity();
        quantity.setProduct(product);
        quantity.setQuantity(quantityDTO.getQuantity());
        quantity.setOperation(Quantity.OperationType.OUT);
        quantity.setDate(LocalDateTime.now());

        return convertToDTO(quantityRepository.save(quantity));
    }

    public int getAvailableQuantity(String productCode) {
        List<Quantity> quantities = quantityRepository.findByProductCode(productCode);
        return quantities.stream()
                .mapToInt(q -> q.getOperation() == Quantity.OperationType.IN ? q.getQuantity() : -q.getQuantity())
                .sum();
    }

    public List<QuantityDTO> getQuantityHistory(String productCode) {
        return quantityRepository.findByProductCode(productCode)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private QuantityDTO convertToDTO(Quantity quantity) {
        QuantityDTO dto = new QuantityDTO();
        dto.setId(quantity.getId());
        dto.setProductCode(quantity.getProduct().getCode());
        dto.setProductName(quantity.getProduct().getName());
        dto.setQuantity(quantity.getQuantity());
        dto.setOperation(quantity.getOperation().name());
        dto.setDate(quantity.getDate());
        return dto;
    }
} 