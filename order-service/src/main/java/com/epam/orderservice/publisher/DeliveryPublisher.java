package com.epam.orderservice.publisher;

import com.epam.orderservice.dto.DeliveryDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import java.util.logging.Logger;

public class DeliveryPublisher {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;


    public DeliveryPublisher(RedisTemplate<?, ?> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(DeliveryDto.class));
        this.topic = topic;
    }

    public void publish(DeliveryDto deliveryDto) {
        logger.info("Sending " + deliveryDto);
        redisTemplate.convertAndSend(topic.getTopic(), deliveryDto);
    }
}
