package com.epam.kitchenservice.subscriber;

import com.epam.kitchenservice.dto.HistoryEvent;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@Slf4j
@NoArgsConstructor
public class KitchenSubscriber implements MessageListener {
    private KitchenService kitchenService;
    private ObjectMapper objectMapper;
    private TicketMapper ticketMapper;


    @Autowired
    public KitchenSubscriber(KitchenService kitchenService, ObjectMapper objectMapper, TicketMapper ticketMapper) {
        this.objectMapper = objectMapper;
        this.kitchenService = kitchenService;
        this.ticketMapper = ticketMapper;

    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        Event event;

        TicketDto ticketDto = objectMapper.readValue(message.getBody(), TicketDto.class);

        log.info("Ticket " + ticketDto + "received");

        if (ticketDto.getAbort()) kitchenService.deleteTicket(ticketDto.getOrderId());
        else {
            Ticket ticket = ticketMapper.toEntity(ticketDto);
            ticket.setCreationTime(Timestamp.from(Instant.now()));
            ticket.setTicketNumber("A35");
            kitchenService.save(ticket);

            event = Event.builder()
                    .orderId(ticket.getOrderId())
                    .eventType(EventType.KITCHEN)
                    .build();
            if (ticket.getOrderId() % 2 != 0) {
                event.setEventResult(EventResult.SUCCESS);
                kitchenService.save(ticket);
                HistoryEvent historyEvent = HistoryEvent.builder()
                        .eventType(EventType.KITCHEN)
                        .body(ticket)
                        .build();
                kitchenService.publishHistoryEvent(historyEvent);
            } else event.setEventResult(EventResult.FAILED);
            kitchenService.publishEvent(event);
        }
    }
}
