package com.epam.deliveryservice.service;

import com.epam.deliveryservice.dto.HistoryEvent;
import com.epam.deliveryservice.entity.Delivery;
import com.epam.deliveryservice.event.Event;

public interface DeliveryService {
    void save(Delivery delivery);
    void publishEvent(Event event);
    void publishHistoryEvent(HistoryEvent historyEvent);
    void removeDelivery(Long orderId);

}
