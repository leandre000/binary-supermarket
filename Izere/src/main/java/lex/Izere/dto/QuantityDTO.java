package lex.Izere.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QuantityDTO {
    private Long id;
    private String productCode;
    private String productName;
    private Integer quantity;
    private String operation;
    private LocalDateTime date;
} 