package com.epam.orderservice.service;

import com.epam.orderservice.dto.DeliveryDto;
import com.epam.orderservice.dto.HistoryEvent;
import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.dto.TicketDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.event.Event;

public interface OrderService {
    void save(Order order);
    Order findById(Long id);
    void compensateOrder(Long orderId);
    DeliveryDto publishDelivery(DeliveryDto deliveryDto);
    void publishPayment(PaymentDto paymentDto);
    void publishTicket(TicketDto ticketDto);
    void publishCompensationEvent(Order order);
    void publishOrderEvent(Event event);
    void publishHistoryEvent(HistoryEvent historyEvent);
}
