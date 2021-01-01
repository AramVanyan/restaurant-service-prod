package com.epam.kitchenservice.mapper;

import com.epam.kitchenservice.dto.TicketDto;
import com.epam.kitchenservice.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket toEntity(TicketDto orderDto);
    TicketDto toDto(Ticket order);
}