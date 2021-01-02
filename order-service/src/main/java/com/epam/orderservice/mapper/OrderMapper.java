package com.epam.orderservice.mapper;

import com.epam.orderservice.dto.OrderDto;
import com.epam.orderservice.entity.Order;

public class OrderMapper {
    public Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .userId(orderDto.getUserId())
                .sum(orderDto.getSum())
                .deliveryTime(orderDto.getDeliveryTime())
                .creditCard(orderDto.getCreditCard())
                .orderDescription(orderDto.getOrderDescription())
                .build();
    }
    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .userId(order.getUserId())
                .sum(order.getSum())
                .deliveryTime(order.getDeliveryTime())
                .creditCard(order.getCreditCard())
                .orderDescription(order.getOrderDescription())
                .build();
    }
}