package com.epam.deliveryservice.service;

import com.epam.deliveryservice.entity.Delivery;
import com.epam.deliveryservice.event.Event;

public interface DeliveryService {
    void save(Delivery delivery);
    Event publishEvent(Event event);
    Delivery publishHistoryEvent(Delivery delivery);

}
