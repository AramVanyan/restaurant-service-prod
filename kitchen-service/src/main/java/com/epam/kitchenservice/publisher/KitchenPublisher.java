package com.epam.kitchenservice.publisher;

import com.epam.kitchenservice.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Slf4j
public class KitchenPublisher {

    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;


    public KitchenPublisher(RedisTemplate<?, ?> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Event.class));
        this.topic = topic;
    }

    public void publish(Event event) {
        log.info("Sending " + event);
        redisTemplate.convertAndSend(topic.getTopic(), event);
    }

}
