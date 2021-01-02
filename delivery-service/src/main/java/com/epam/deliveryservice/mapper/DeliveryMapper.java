package com.epam.deliveryservice.mapper;

import com.epam.deliveryservice.dto.DeliveryDto;
import com.epam.deliveryservice.entity.Delivery;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {
    public Delivery toEntity(DeliveryDto deliveryDto) {
        return Delivery.builder()
                .orderId(deliveryDto.getOrderId())
                .scheduledDeliveryTime(deliveryDto.getScheduledDeliveryTime())
                .build();
    }
    public DeliveryDto toDto(Delivery order) {
        return DeliveryDto.builder()
                .orderId(order.getOrderId())
                .scheduledDeliveryTime(order.getScheduledDeliveryTime())
                .build();
    }
}