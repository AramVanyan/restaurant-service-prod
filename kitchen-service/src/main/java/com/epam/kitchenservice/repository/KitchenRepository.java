package com.epam.kitchenservice.repository;

import com.epam.kitchenservice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends JpaRepository<Ticket,Long> {
    Ticket deleteByOrderId(Long orderId);
}
