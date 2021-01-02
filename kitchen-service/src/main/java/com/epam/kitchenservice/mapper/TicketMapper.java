package com.epam.kitchenservice.mapper;

import com.epam.kitchenservice.dto.TicketDto;
import com.epam.kitchenservice.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public Ticket toEntity(TicketDto ticketDto) {
        return Ticket.builder()
                .orderId(ticketDto.getOrderId())
                .build();
    }
    public TicketDto toDto(Ticket order) {
        return TicketDto.builder()
                .orderId(order.getOrderId())
                .build();
    }
}