package com.epam.orderservice.publisher;

import com.epam.orderservice.dto.TicketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Slf4j
public class KitchenPublisher {
    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;

    public KitchenPublisher(RedisTemplate<?, ?> redisTemplate, @Qualifier("kitchenTopic") ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(TicketDto.class));
        this.topic = topic;
    }

    public void publish(TicketDto ticketDto) {
        log.info("Sending " + ticketDto);
        redisTemplate.convertAndSend(topic.getTopic(), ticketDto);
    }
}
