package lex.Izere.controller;

import lex.Izere.dto.CartItemDTO;
import lex.Izere.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{customerId}")
    public List<CartItemDTO> getCartItems(@PathVariable Long customerId) {
        return cartService.getCartItems(customerId);
    }

    @PostMapping("/{customerId}/add")
    public ResponseEntity<CartItemDTO> addToCart(
            @PathVariable Long customerId,
            @RequestParam String productCode,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addToCart(customerId, productCode, quantity));
    }

    @PutMapping("/{customerId}/update/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @PathVariable Long customerId,
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(customerId, cartItemId, quantity));
    }

    @DeleteMapping("/{customerId}/remove/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable Long customerId,
            @PathVariable Long cartItemId) {
        cartService.removeFromCart(customerId, cartItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.ok().build();
    }
} 