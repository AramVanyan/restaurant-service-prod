package com.epam.kitchenservice.subscriber;

import com.epam.kitchenservice.dto.TicketDto;
import com.epam.kitchenservice.entity.Ticket;
import com.epam.kitchenservice.event.Event;
import com.epam.kitchenservice.event.EventResult;
import com.epam.kitchenservice.event.EventType;
import com.epam.kitchenservice.mapper.TicketMapper;
import com.epam.kitchenservice.service.KitchenService;
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
public class KitchenSubscriber implements MessageListener {
    private KitchenService kitchenService;
    private ObjectMapper objectMapper;
    private final TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);


    @Autowired
    public KitchenSubscriber(KitchenService kitchenService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.kitchenService = kitchenService;

    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        Event event;

        TicketDto ticketDto = objectMapper.readValue(message.getBody(), TicketDto.class);

        if (ticketDto.getToBeCompensated()) kitchenService.compensateTicket(ticketDto.getOrderId());
        else {
            Ticket ticket = ticketMapper.toEntity(ticketDto);
            kitchenService.save(ticket);

            String sDate1 = "31/12/2010";
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

            event = Event.builder()
                    .orderId(ticket.getOrderId())
                    .eventType(EventType.PAYMENT)
                    .build();

            if (ticket.getCreationTime().after(date)) {
                event.setEventResult(EventResult.SUCCESS);
                kitchenService.save(ticket);
            } else event.setEventResult(EventResult.FAILED);

            kitchenService.publishEvent(event);
            kitchenService.publishHistoryEvent(ticket);
        }
    }
}
