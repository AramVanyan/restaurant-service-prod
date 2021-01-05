package com.epam.orderservice.publisher;

import com.epam.orderservice.dto.DeliveryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Slf4j
public class DeliveryPublisher {
    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;

    public DeliveryPublisher(RedisTemplate<?, ?> redisTemplate, @Qualifier("deliveryTopic") ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(DeliveryDto.class));
        this.topic = topic;
    }

    public void publish(DeliveryDto deliveryDto) {
        log.info("Sending " + deliveryDto);
        redisTemplate.convertAndSend(topic.getTopic(), deliveryDto);
    }
}
