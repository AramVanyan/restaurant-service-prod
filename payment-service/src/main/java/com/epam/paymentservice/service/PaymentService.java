package com.epam.paymentservice.service;


import com.epam.paymentservice.dto.HistoryEvent;
import com.epam.paymentservice.entity.Payment;
import com.epam.paymentservice.event.Event;

public interface PaymentService {
    void save(Payment payment);
    void deletePayment(Long orderId);
    void publishEvent(Event event);
    void publishHistoryEvent(HistoryEvent historyEvent);
}
