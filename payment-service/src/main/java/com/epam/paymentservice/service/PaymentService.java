package com.epam.paymentservice.service;


import com.epam.paymentservice.entity.Payment;
import com.epam.paymentservice.event.Event;

public interface PaymentService {
    Payment save(Payment payment);
    Payment compensatePayment(Long orderId);
    Event publishEvent(Event event);
}
