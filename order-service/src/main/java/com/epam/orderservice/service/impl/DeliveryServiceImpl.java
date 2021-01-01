package com.epam.orderservice.service.impl;

import com.epam.orderservice.dto.DeliveryDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.service.DeliveryService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Override
    public DeliveryDto composeDelivery(Order order,Boolean toBeCompensated) {
        return new DeliveryDto(order.getId(),order.getDeliveryTime(),toBeCompensated);
    }
}
