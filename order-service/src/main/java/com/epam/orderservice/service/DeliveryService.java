package com.epam.orderservice.service;

import com.epam.orderservice.dto.DeliveryDto;
import com.epam.orderservice.entity.Order;

public interface DeliveryService {
    DeliveryDto composeDelivery(Order order,Boolean toBeCompensated);
}
