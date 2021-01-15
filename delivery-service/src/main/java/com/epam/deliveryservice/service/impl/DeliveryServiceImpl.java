package com.epam.deliveryservice.service.impl;

import com.epam.deliveryservice.dto.HistoryEvent;
import com.epam.deliveryservice.entity.Delivery;
import com.epam.deliveryservice.event.Event;
import com.epam.deliveryservice.publisher.DeliveryHistoryPublisher;
import com.epam.deliveryservice.publisher.DeliveryPublisher;
import com.epam.deliveryservice.repository.DeliveryRepository;
import com.epam.deliveryservice.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryPublisher deliveryPublisher;
    private final DeliveryHistoryPublisher historyPublisher;

    @Override
    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    @Override
    public void publishEvent(Event event) {
        deliveryPublisher.publish(event);
    }

    @Override
    public void publishHistoryEvent(HistoryEvent historyEvent) {
        historyPublisher.publish(historyEvent);
    }

    @Override
    public void removeDelivery(Long orderId) {
        deliveryRepository.deleteByOrderId(orderId);
    }
}
