package com.epam.historyservice.subscriber;

import com.epam.historyservice.model.Delivery;
import com.epam.historyservice.model.Order;
import com.epam.historyservice.model.Payment;
import com.epam.historyservice.model.Ticket;
import com.epam.historyservice.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@NoArgsConstructor
public class HistorySubscriber implements MessageListener {


    private ObjectMapper objectMapper;
    private HistoryService historyService;


    @Autowired
    public HistorySubscriber(ObjectMapper objectMapper,HistoryService historyService) {
        this.objectMapper=objectMapper;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
           Payment info = objectMapper.readValue(message.getBody(), Payment.class);
           historyService.addData(info);

        }catch(RuntimeException ex){}

        try {
            Order info = objectMapper.readValue(message.getBody(), Order.class);
            historyService.addData(info);
        }catch(RuntimeException ex){}

        try {
            Ticket info = objectMapper.readValue(message.getBody(), Ticket.class);
            historyService.addData(info);
        }catch(RuntimeException ex){}

        try {
            Delivery info = objectMapper.readValue(message.getBody(), Delivery.class);
            historyService.addData(info);
        }catch(RuntimeException ex){}

    }
}

