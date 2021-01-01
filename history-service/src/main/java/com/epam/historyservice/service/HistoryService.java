package com.epam.historyservice.service;

import com.epam.historyservice.entity.OrderDetails;
import com.epam.historyservice.model.Delivery;
import com.epam.historyservice.model.Order;
import com.epam.historyservice.model.Payment;
import com.epam.historyservice.model.Ticket;

public interface HistoryService {

    OrderDetails getOrderDetails(Long id);
    void addData(Payment payment);
    void addData(Ticket ticket);
    void addData(Order order);
    void addData(Delivery Delivery);

}
