package com.epam.paymentservice.publisher;



import com.epam.paymentservice.entity.Payment;
import com.epam.paymentservice.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventPublisher {

    private final RedisTemplate<?, ?> redisTemplate;
    private final ChannelTopic topic;


    @Autowired
    public EventPublisher(RedisTemplate<?, ?> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Payment.class));
        this.topic = topic;
    }

    public void publish(Event event) {
        log.info("Sending " + event);
        redisTemplate.convertAndSend(topic.getTopic(), event);
    }
}
