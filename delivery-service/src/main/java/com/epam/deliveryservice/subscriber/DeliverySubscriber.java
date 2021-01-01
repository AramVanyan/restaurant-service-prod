package com.epam.deliveryservice.subscriber;

import com.epam.deliveryservice.dto.DeliveryDto;
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
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@Slf4j
@NoArgsConstructor
public class DeliverySubscriber implements MessageListener {
    private DeliveryService deliveryService;
    private ObjectMapper objectMapper;
    private final DeliveryMapper deliveryMapper = Mappers.getMapper(DeliveryMapper.class);


    @Autowired
    public DeliverySubscriber(DeliveryService deliveryService,ObjectMapper objectMapper) {
        this.objectMapper=objectMapper;
        this.deliveryService = deliveryService;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        Event event;
        DeliveryDto deliveryDto = objectMapper.readValue(message.getBody(), DeliveryDto.class);
        Delivery delivery=deliveryMapper.toEntity(deliveryDto);

        String sDate1 = "31/12/2009";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

        event = Event.builder()
                .eventType(EventType.DELIVERY)
                .orderId(delivery.getOrderId())
                .build();

        if (delivery.getScheduledDeliveryTime().after(date)) {
            deliveryService.save(delivery);
            event.setEventResult(EventResult.SUCCESS);
        } else event.setEventResult(EventResult.FAILED);

        deliveryService.publishEvent(event);
        deliveryService.publishHistoryEvent(delivery);
    }
}
