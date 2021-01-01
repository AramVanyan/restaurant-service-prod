package com.epam.orderservice.service;

import com.epam.orderservice.dto.DeliveryDto;
import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.entity.Order;

public interface PaymentService {
    PaymentDto composePayment(Order order,Boolean toBeCompensated);
}
