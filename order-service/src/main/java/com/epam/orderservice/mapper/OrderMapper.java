package com.epam.orderservice.mapper;

import com.epam.orderservice.dto.OrderDto;
import com.epam.orderservice.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .userId(orderDto.getUserId())
                .orderSum(orderDto.getOrderSum())
                .creditCard(orderDto.getCreditCard())
                .orderDescription(orderDto.getOrderDescription())
                .deliveryTime(orderDto.getDeliveryTime())
                .build();
    }
    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .userId(order.getUserId())
                .orderSum(order.getOrderSum())
                .deliveryTime(order.getDeliveryTime())
                .creditCard(order.getCreditCard())
                .orderDescription(order.getOrderDescription())
                .build();
    }
}