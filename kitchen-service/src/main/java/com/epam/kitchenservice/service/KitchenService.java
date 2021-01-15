package com.epam.kitchenservice.service;

import com.epam.kitchenservice.dto.HistoryEvent;
import com.epam.kitchenservice.entity.Ticket;
import com.epam.kitchenservice.event.Event;

public interface KitchenService {
    void deleteTicket(Long orderId);
    void save(Ticket ticket);
    void publishEvent(Event event);
    void publishHistoryEvent(HistoryEvent historyEvent);
}
