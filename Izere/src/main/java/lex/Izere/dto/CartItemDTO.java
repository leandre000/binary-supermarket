package lex.Izere.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long id;
    private Long customerId;
    private String productCode;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
    private LocalDateTime createdAt;
} 