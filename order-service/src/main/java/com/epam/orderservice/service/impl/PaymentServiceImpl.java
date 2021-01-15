package com.epam.orderservice.service.impl;

import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentDto composePayment(Order order, Boolean abort) {
        return PaymentDto.builder()
                .sum(order.getOrderSum())
                .orderId(order.getId())
                .creditCardDto(order.getCreditCard())
                .abort(abort)
                .build();
    }
}
