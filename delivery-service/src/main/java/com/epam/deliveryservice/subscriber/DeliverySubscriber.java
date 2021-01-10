package com.epam.deliveryservice.subscriber;

import com.epam.deliveryservice.dto.DeliveryDto;
import com.epam.deliveryservice.dto.HistoryEvent;
import com.epam.deliveryservice.entity.Delivery;
import com.epam.deliveryservice.event.Event;
import com.epam.deliveryservice.event.EventResult;
import com.epam.deliveryservice.event.EventType;
import com.epam.deliveryservice.mapper.DeliveryMapper;
import com.epam.deliveryservice.service.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
@NoArgsConstructor
public class DeliverySubscriber implements MessageListener {
    private DeliveryService deliveryService;
    private ObjectMapper objectMapper;
    private DeliveryMapper deliveryMapper;


    @Autowired
    public DeliverySubscriber(DeliveryService deliveryService, ObjectMapper objectMapper, DeliveryMapper deliveryMapper) {
        this.objectMapper = objectMapper;
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        Event event;
        DeliveryDto deliveryDto = objectMapper.readValue(message.getBody(), DeliveryDto.class);
        Delivery delivery = deliveryMapper.toEntity(deliveryDto);
        delivery.setCompletionTime(Timestamp.from(Instant.now()));
        String sDate1 = "1/1/2020";
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

        String sDate2 = "7/1/2020";
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);

        event = Event.builder()
                .eventType(EventType.DELIVERY)
                .orderId(delivery.getOrderId())
                .build();

        if (delivery.getScheduledDeliveryTime().after(date1) && delivery.getScheduledDeliveryTime().before(date2)) {
            deliveryService.save(delivery);
            event.setEventResult(EventResult.SUCCESS);
            HistoryEvent historyEvent = HistoryEvent.builder()
                    .eventType(EventType.DELIVERY)
                    .body(delivery)
                    .build();
            deliveryService.publishHistoryEvent(historyEvent);
        } else event.setEventResult(EventResult.FAILED);
        deliveryService.publishEvent(event);
    }
}
