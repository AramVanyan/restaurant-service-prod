package com.epam.orderservice.service.impl;

import com.epam.orderservice.dto.DeliveryDto;
import com.epam.orderservice.dto.HistoryEvent;
import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.dto.TicketDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.event.Event;
import com.epam.orderservice.publisher.*;
import com.epam.orderservice.repository.OrderRepository;
import com.epam.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final PaymentPublisher paymentPublisher;
    private final KitchenPublisher kitchenPublisher;
    private final DeliveryPublisher deliveryPublisher;
    private final OrderRepository orderRepository;
    private final OrderHistoryPublisher historyPublisher;
    private final EventPublisher eventPublisher;


    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order compensateOrder(Long orderId) {
        Order order = orderRepository.getOne(orderId);
        orderRepository.deleteById(orderId);
        return order;
    }

    @Override
    public DeliveryDto publishDelivery(DeliveryDto deliveryDto) {
        deliveryPublisher.publish(deliveryDto);
        return deliveryDto;
    }

    @Override
    public PaymentDto publishPayment(PaymentDto paymentDto) {
        paymentPublisher.publish(paymentDto);
        return paymentDto;
    }

    @Override
    public TicketDto publishTicket(TicketDto ticketDto) {
        kitchenPublisher.publish(ticketDto);
        return ticketDto;
    }

    @Override
    public void publishCompensationEvent(Order order) {

    }

    @Override
    public void publishEvent(Event event) {
        eventPublisher.publish(event);
    }

    @Override
    public void publishHistoryEvent(HistoryEvent historyEvent) {
        historyPublisher.publish(historyEvent);
    }
}
