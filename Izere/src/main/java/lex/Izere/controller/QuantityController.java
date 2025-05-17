package lex.Izere.controller;

import lex.Izere.dto.QuantityDTO;
import lex.Izere.service.QuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quantities")
@CrossOrigin(origins = "*")
public class QuantityController {
    @Autowired
    private QuantityService quantityService;

    @GetMapping("/product/{productCode}")
    public List<QuantityDTO> getQuantitiesByProduct(@PathVariable String productCode) {
        return quantityService.getQuantitiesByProduct(productCode);
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityDTO> addQuantity(@RequestBody QuantityDTO quantityDTO) {
        return ResponseEntity.ok(quantityService.addQuantity(quantityDTO));
    }

    @PostMapping("/remove")
    public ResponseEntity<QuantityDTO> removeQuantity(@RequestBody QuantityDTO quantityDTO) {
        return ResponseEntity.ok(quantityService.removeQuantity(quantityDTO));
    }

    @GetMapping("/available/{productCode}")
    public ResponseEntity<Integer> getAvailableQuantity(@PathVariable String productCode) {
        return ResponseEntity.ok(quantityService.getAvailableQuantity(productCode));
    }

    @GetMapping("/history/{productCode}")
    public List<QuantityDTO> getQuantityHistory(@PathVariable String productCode) {
        return quantityService.getQuantityHistory(productCode);
    }
} 