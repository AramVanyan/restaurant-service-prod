package com.epam.historyservice.subscriber;

import com.epam.historyservice.dto.HistoryEvent;
import com.epam.historyservice.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@NoArgsConstructor
public class HistorySubscriber implements MessageListener {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private HistoryService historyService;

    @Autowired
    public HistorySubscriber(HistoryService historyService) {
        this.historyService = historyService;
    }

    @SneakyThrows
    @Override
    public synchronized void onMessage(Message message, byte[] bytes) {
        HistoryEvent historyEvent = objectMapper.readValue(message.getBody(), HistoryEvent.class);

        switch (historyEvent.getEventType()) {
            case PAYMENT -> {
                historyService.addPaymentInfo(historyEvent.getBody());
                log.info("add payment history");
            }
            case KITCHEN -> {
                historyService.addTicketInfo(historyEvent.getBody());
                log.info("add ticket history");
            }
            case DELIVERY -> {
                historyService.addDeliveryInfo(historyEvent.getBody());
                log.info("add delivery history");
            }
            case ORDER -> {
                historyService.addOrderInfo(historyEvent.getBody());
                log.info("add order history");
            }
        }
    }
}

