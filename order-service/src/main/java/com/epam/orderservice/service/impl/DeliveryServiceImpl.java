package com.epam.orderservice.service.impl;

import com.epam.orderservice.dto.DeliveryDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.publisher.DeliveryPublisher;
import com.epam.orderservice.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private DeliveryPublisher deliveryPublisher;

    @Override
    public DeliveryDto composeDelivery(Order order,Boolean abort) {
        return new DeliveryDto(order.getId(),order.getDeliveryTime(),abort);
    }

    @Override
    public void publishDelivery(DeliveryDto deliveryDto) {
        deliveryPublisher.publish(deliveryDto);
    }

    @Autowired
    public void setDeliveryPublisher(DeliveryPublisher deliveryPublisher) {
        this.deliveryPublisher = deliveryPublisher;
    }
}
