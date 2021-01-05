package com.epam.orderservice.service.impl;

import com.epam.orderservice.dto.TicketDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.publisher.KitchenPublisher;
import com.epam.orderservice.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KitchenServiceImpl implements KitchenService {
    private KitchenPublisher kitchenPublisher;

    @Autowired
    public KitchenServiceImpl(KitchenPublisher kitchenPublisher) {
        this.kitchenPublisher = kitchenPublisher;
    }

    @Override
    public TicketDto composeTicket(Order order, Boolean toBeCompensated) {
        return new TicketDto(order.getId(),toBeCompensated);
    }

    @Override
    public void publishTicket(TicketDto ticketDto) {
        kitchenPublisher.publish(ticketDto);
    }
}
