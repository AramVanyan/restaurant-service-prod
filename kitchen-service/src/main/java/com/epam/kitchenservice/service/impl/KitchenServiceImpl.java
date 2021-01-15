package com.epam.kitchenservice.service.impl;

import com.epam.kitchenservice.dto.HistoryEvent;
import com.epam.kitchenservice.entity.Ticket;
import com.epam.kitchenservice.event.Event;
import com.epam.kitchenservice.publisher.KitchenHistoryPublisher;
import com.epam.kitchenservice.publisher.KitchenPublisher;
import com.epam.kitchenservice.repository.KitchenRepository;
import com.epam.kitchenservice.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {
    private final KitchenRepository kitchenRepository;
    private final KitchenPublisher kitchenPublisher;
    private final KitchenHistoryPublisher historyPublisher;

    @Override
    public void deleteTicket(Long orderId) {
        kitchenRepository.deleteByOrderId(orderId);
    }

    @Override
    public void save(Ticket ticket) {
        kitchenRepository.save(ticket);
    }

    @Override
    public void publishEvent(Event event) {
        kitchenPublisher.publish(event);
    }

    @Override
    public void publishHistoryEvent(HistoryEvent historyEvent) {
        historyPublisher.publish(historyEvent);
    }
}
