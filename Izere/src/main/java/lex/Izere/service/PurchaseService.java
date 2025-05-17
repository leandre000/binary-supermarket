package lex.Izere.service;

import lex.Izere.dto.CartItemDTO;
import lex.Izere.dto.PurchaseDTO;
import lex.Izere.exception.BusinessException;
import lex.Izere.exception.ResourceNotFoundException;
import lex.Izere.model.CartItem;
import lex.Izere.model.Customer;
import lex.Izere.model.Product;
import lex.Izere.model.Purchase;
import lex.Izere.repository.CartItemRepository;
import lex.Izere.repository.CustomerRepository;
import lex.Izere.repository.ProductRepository;
import lex.Izere.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public PurchaseDTO buyProduct(Long customerId, String productCode, Integer quantity) {
        // Validate customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        // Validate product
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with code: " + productCode));

        // Validate quantity
        if (quantity <= 0) {
            throw new BusinessException("Quantity must be greater than zero");
        }

        // Create purchase
        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setProduct(product);
        purchase.setQuantity(quantity);
        purchase.setTotal(product.getPrice().multiply(new java.math.BigDecimal(quantity)));
        purchase.setPurchaseDate(LocalDateTime.now());

        // Save purchase
        return convertToDTO(purchaseRepository.save(purchase));
    }

    @Transactional
    public List<PurchaseDTO> checkout(Long customerId) {
        // Validate customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        // Get cart items
        List<CartItem> cartItems = cartItemRepository.findByCustomerId(customerId);
        if (cartItems.isEmpty()) {
            throw new BusinessException("Cart is empty");
        }

        // Create purchases from cart items
        List<Purchase> purchases = cartItems.stream()
                .map(cartItem -> {
                    Purchase purchase = new Purchase();
                    purchase.setCustomer(customer);
                    purchase.setProduct(cartItem.getProduct());
                    purchase.setQuantity(cartItem.getQuantity());
                    purchase.setTotal(cartItem.getProduct().getPrice()
                            .multiply(new java.math.BigDecimal(cartItem.getQuantity())));
                    purchase.setPurchaseDate(LocalDateTime.now());
                    return purchase;
                })
                .collect(Collectors.toList());

        // Save all purchases
        List<Purchase> savedPurchases = purchaseRepository.saveAll(purchases);
        
        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return savedPurchases.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PurchaseDTO> getPurchaseHistory(Long customerId) {
        return purchaseRepository.findByCustomerId(customerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PurchaseDTO convertToDTO(Purchase purchase) {
        PurchaseDTO dto = new PurchaseDTO();
        dto.setId(purchase.getId());
        dto.setCustomerId(purchase.getCustomer().getId());
        dto.setCustomerName(purchase.getCustomer().getFirstname());
        dto.setProductCode(purchase.getProduct().getCode());
        dto.setProductName(purchase.getProduct().getName());
        dto.setQuantity(purchase.getQuantity());
        dto.setTotal(purchase.getTotal());
        dto.setPurchaseDate(purchase.getPurchaseDate());
        return dto;
    }
} 