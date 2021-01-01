package com.epam.orderservice.publisher;

import com.epam.orderservice.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderHistoryPublisher {
        private final RedisTemplate<?, ?> redisTemplate;

        private final ChannelTopic topic;


        @Autowired
        public OrderHistoryPublisher(RedisTemplate<?, ?> redisTemplate, @Qualifier("history") ChannelTopic topic) {
            this.redisTemplate = redisTemplate;
            this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Order.class));
            this.topic = topic;
        }

        public void publish(Order order) {
            log.info("Sending " + order);
            redisTemplate.convertAndSend(topic.getTopic(), order);
        }

}
