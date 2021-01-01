package com.epam.userservice.publisher;

import lombok.extern.slf4j.Slf4j;
import com.epam.userservice.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderPublisher {
    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;


    @Autowired
    public OrderPublisher(RedisTemplate<?, ?> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(OrderDto.class));
        this.topic = topic;
    }

    public void publish(OrderDto order) {
        log.info("Sending " + order);
        redisTemplate.convertAndSend(topic.getTopic(), order);
    }

}
