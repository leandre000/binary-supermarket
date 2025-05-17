package lex.Izere.service;

import lex.Izere.dto.CartItemDTO;
import lex.Izere.exception.BusinessException;
import lex.Izere.exception.ResourceNotFoundException;
import lex.Izere.model.CartItem;
import lex.Izere.model.Customer;
import lex.Izere.model.Product;
import lex.Izere.repository.CartItemRepository;
import lex.Izere.repository.CustomerRepository;
import lex.Izere.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItemDTO> getCartItems(Long customerId) {
        return cartItemRepository.findByCustomerId(customerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartItemDTO addToCart(Long customerId, String productCode, Integer quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCustomer(customer);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCreatedAt(LocalDateTime.now());

        return convertToDTO(cartItemRepository.save(cartItem));
    }

    @Transactional
    public CartItemDTO updateCartItem(Long customerId, Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCustomer().getId().equals(customerId)) {
            throw new BusinessException("Cart item does not belong to the customer");
        }

        cartItem.setQuantity(quantity);
        return convertToDTO(cartItemRepository.save(cartItem));
    }

    @Transactional
    public void removeFromCart(Long customerId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCustomer().getId().equals(customerId)) {
            throw new BusinessException("Cart item does not belong to the customer");
        }

        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart(Long customerId) {
        List<CartItem> cartItems = cartItemRepository.findByCustomerId(customerId);
        cartItemRepository.deleteAll(cartItems);
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setCustomerId(cartItem.getCustomer().getId());
        dto.setProductCode(cartItem.getProduct().getCode());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setProductPrice(cartItem.getProduct().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        dto.setCreatedAt(cartItem.getCreatedAt());
        return dto;
    }
} 