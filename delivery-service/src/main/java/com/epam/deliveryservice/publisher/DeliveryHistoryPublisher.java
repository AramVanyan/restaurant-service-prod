package com.epam.deliveryservice.publisher;


import com.epam.deliveryservice.entity.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeliveryHistoryPublisher {

    private final RedisTemplate<?, ?> redisTemplate;

    private final ChannelTopic topic;


    @Autowired
    public DeliveryHistoryPublisher(RedisTemplate<?, ?> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Delivery.class));
        this.topic = topic;
    }

    public void publish(Delivery delivery) {
        log.info("Sending " + delivery);
        redisTemplate.convertAndSend(topic.getTopic(), delivery);
    }

}
