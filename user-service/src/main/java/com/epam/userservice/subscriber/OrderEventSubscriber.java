package com.epam.userservice.subscriber;

import com.epam.userservice.event.Event;
import com.epam.userservice.event.EventResult;
import com.epam.userservice.event.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderEventSubscriber implements MessageListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        Event event = objectMapper.readValue(message.getBody(), Event.class);

        if (event.getEventType().equals(EventType.ORDER)) {
            if (event.getEventResult().equals(EventResult.SUCCESS)) log.info("order succeed");
            else log.info("order failed");
        }
    }
}
