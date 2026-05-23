package project.repository;

import project.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    void deleteAllByCart_Id(Long id);
}
