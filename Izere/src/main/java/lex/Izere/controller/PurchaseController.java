package lex.Izere.controller;

import lex.Izere.dto.PurchaseDTO;
import lex.Izere.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin(origins = "*")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/{customerId}/buy")
    public ResponseEntity<PurchaseDTO> buyProduct(
            @PathVariable Long customerId,
            @RequestParam String productCode,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(purchaseService.buyProduct(customerId, productCode, quantity));
    }

    @PostMapping("/{customerId}/checkout")
    public ResponseEntity<List<PurchaseDTO>> checkout(@PathVariable Long customerId) {
        return ResponseEntity.ok(purchaseService.checkout(customerId));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<PurchaseDTO>> getPurchaseHistory(@PathVariable Long customerId) {
        return ResponseEntity.ok(purchaseService.getPurchaseHistory(customerId));
    }
} 