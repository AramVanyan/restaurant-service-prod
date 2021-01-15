package com.epam.paymentservice.service.impl;

import com.epam.paymentservice.dto.HistoryEvent;
import com.epam.paymentservice.entity.Payment;
import com.epam.paymentservice.event.Event;
import com.epam.paymentservice.publisher.EventPublisher;
import com.epam.paymentservice.publisher.PaymentHistoryPublisher;
import com.epam.paymentservice.repository.PaymentRepository;
import com.epam.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentHistoryPublisher paymentHistoryPublisher;
    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long orderId) {
        paymentRepository.deletePaymentByOrderId(orderId);
    }

    @Override
    public void publishEvent(Event event) {
        eventPublisher.publish(event);
    }

    @Override
    public void publishHistoryEvent(HistoryEvent historyEvent) {
        paymentHistoryPublisher.publish(historyEvent);
    }
}
