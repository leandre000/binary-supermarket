package lex.Izere.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PurchaseDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private String productCode;
    private String productName;
    private Integer quantity;
    private BigDecimal total;
    private LocalDateTime purchaseDate;
} 