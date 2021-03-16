package com.epam.kitchenservice.publisher;

import com.epam.kitchenservice.dto.HistoryEvent;
import com.epam.kitchenservice.entity.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Slf4j
public class KitchenHistoryPublisher {
    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;

    public KitchenHistoryPublisher(RedisTemplate<?, ?> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Ticket.class));
        this.topic = topic;
    }

    public void publish(HistoryEvent historyEvent) {
        redisTemplate.convertAndSend(topic.getTopic(), historyEvent);
        log.info("Published history event " + historyEvent);
    }
}
