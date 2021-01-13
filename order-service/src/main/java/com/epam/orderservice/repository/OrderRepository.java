package com.epam.orderservice.repository;

import com.epam.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Override
    <S extends Order> S save(S s);
    void deleteById(Long id);
}
