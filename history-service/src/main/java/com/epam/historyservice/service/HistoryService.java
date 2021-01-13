package com.epam.historyservice.service;

import com.epam.historyservice.entity.OrderDetails;

import java.util.LinkedHashMap;

public interface HistoryService {

    void addPaymentInfo(Object paymentInfo);
    void addTicketInfo(Object ticketInfo);

    void addOrderInfo(Object orderInfo);

    void addDeliveryInfo(Object deliveryInfo);


}
