package com.epam.orderservice.mapper;

import com.epam.orderservice.dto.OrderDto;
import com.epam.orderservice.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDto orderDto);
    OrderDto toDto(Order order);
}