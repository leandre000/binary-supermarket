package lex.Izere.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private String code;
    private String name;
    private String productType;
    private BigDecimal price;
    private LocalDateTime inDate;
    private String imageUrl;
    private Integer availableQuantity;
} 