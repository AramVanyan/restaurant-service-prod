package com.epam.orderservice.publisher;

import com.epam.orderservice.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Slf4j
public class PaymentPublisher {
    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;

    public PaymentPublisher(RedisTemplate<?, ?> redisTemplate, @Qualifier("paymentTopic") ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(PaymentDto.class));
        this.topic = topic;
    }

    public void publish(PaymentDto payment) {
        log.info("Sending " + payment);
        redisTemplate.convertAndSend(topic.getTopic(), payment);
    }
}
