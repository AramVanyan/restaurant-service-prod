package com.epam.paymentservice.service.impl;

import com.epam.paymentservice.entity.Payment;
import com.epam.paymentservice.event.Event;
import com.epam.paymentservice.publisher.EventPublisher;
import com.epam.paymentservice.repository.PaymentRepository;
import com.epam.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;

    @Override
    public Payment save(Payment payment) {
       return paymentRepository.save(payment);
    }

    @Override
    public Payment compensatePayment(Long orderId) {
        return paymentRepository.deletePaymentByOrderId(orderId);
    }

    @Override
    public Event publishEvent(Event event) {
        eventPublisher.publish(event);
        return event;
    }
}
