package com.epam.orderservice.publisher;

import com.epam.orderservice.dto.HistoryEvent;
import com.epam.orderservice.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Slf4j
public class OrderHistoryPublisher {
        private final RedisTemplate<?, ?> redisTemplate;
        private final ChannelTopic topic;

        public OrderHistoryPublisher(RedisTemplate<?, ?> redisTemplate, @Qualifier("historyTopic") ChannelTopic topic) {
            this.redisTemplate = redisTemplate;
            this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Order.class));
            this.topic = topic;
        }

        public void publish(HistoryEvent historyEvent) {
            log.info("Sending " + historyEvent);
            redisTemplate.convertAndSend(topic.getTopic(), historyEvent);
        }

}
