package com.epam.orderservice.service;

import com.epam.orderservice.dto.DeliveryDto;
import com.epam.orderservice.dto.HistoryEvent;
import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.dto.TicketDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.event.Event;

public interface OrderService {
    Order save(Order order);
    Order findById(Long id);
    Order compensateOrder(Long orderId);
    DeliveryDto publishDelivery(DeliveryDto deliveryDto);
    PaymentDto publishPayment(PaymentDto paymentDto);
    TicketDto publishTicket(TicketDto ticketDto);
    void publishCompensationEvent(Order order);
    void publishEvent(Event event);
    void publishHistoryEvent(HistoryEvent historyEvent);
}
