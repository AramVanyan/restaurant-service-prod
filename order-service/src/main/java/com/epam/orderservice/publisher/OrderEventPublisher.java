package com.epam.orderservice.publisher;

import com.epam.orderservice.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderEventPublisher {
    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;


    public OrderEventPublisher(@Qualifier("orderEventRedisTemplate") RedisTemplate<?, ?> redisTemplate, @Qualifier("orderEventTopic") ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Event.class));
        this.topic = topic;
    }

    public void publish(Event event) {
        log.info("Sending order event to user service" + event);
        redisTemplate.convertAndSend(topic.getTopic(), event);
    }
}
