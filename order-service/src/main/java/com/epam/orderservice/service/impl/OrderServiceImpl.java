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
    private final OrderEventPublisher eventPublisher;


    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void compensateOrder(Long orderId) {
        Order order = orderRepository.getOne(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public DeliveryDto publishDelivery(DeliveryDto deliveryDto) {
        deliveryPublisher.publish(deliveryDto);
        return deliveryDto;
    }

    @Override
    public void publishPayment(PaymentDto paymentDto) {
        paymentPublisher.publish(paymentDto);
    }

    @Override
    public void publishTicket(TicketDto ticketDto) {
        kitchenPublisher.publish(ticketDto);
    }

    @Override
    public void publishCompensationEvent(Order order) {

    }

    @Override
    public void publishOrderEvent(Event event) {
        eventPublisher.publish(event);
    }

    @Override
    public void publishHistoryEvent(HistoryEvent historyEvent) {
        historyPublisher.publish(historyEvent);
    }
}
