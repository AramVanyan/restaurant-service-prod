package com.epam.historyservice.repository;

import com.epam.historyservice.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderDetails,Long> {
    OrderDetails findByOrderId(long id);
}
