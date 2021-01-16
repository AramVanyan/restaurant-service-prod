package com.epam.historyservice.service;

public interface HistoryService {
    void addPaymentInfo(Object paymentInfo);
    void addTicketInfo(Object ticketInfo);
    void addOrderInfo(Object orderInfo);
    void addDeliveryInfo(Object deliveryInfo);
    void deleteOrderDetails(Object orderId);
}
