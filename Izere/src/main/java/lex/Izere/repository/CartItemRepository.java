package lex.Izere.repository;

import lex.Izere.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomerId(Long customerId);
    void deleteByCustomerId(Long customerId);
} 