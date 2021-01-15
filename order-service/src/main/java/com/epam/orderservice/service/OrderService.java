package com.epam.orderservice.service;

import com.epam.orderservice.dto.HistoryEvent;
import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.dto.TicketDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.event.Event;

public interface OrderService {
    Order save(Order order);
    Order findById(Long id);
    void deleteOrder(Long orderId);
    void publishPayment(PaymentDto paymentDto);
    void publishTicket(TicketDto ticketDto);
    void publishOrderEvent(Event event);
    void publishHistoryEvent(HistoryEvent historyEvent);
}
