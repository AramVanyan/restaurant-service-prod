package com.epam.kitchenservice.service;

import com.epam.kitchenservice.entity.Ticket;
import com.epam.kitchenservice.event.Event;

public interface KitchenService {
    Ticket compensateTicket(Long orderId);
    Ticket save(Ticket ticket);
    Event publishEvent(Event event);
    Ticket publishHistoryEvent(Ticket ticket);
}
