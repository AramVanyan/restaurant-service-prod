package com.epam.kitchenservice.publisher;

import com.epam.kitchenservice.entity.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KitchenHistoryPublisher {

        private final RedisTemplate<?, ?> redisTemplate;

        private final ChannelTopic topic;


        @Autowired
        public KitchenHistoryPublisher(RedisTemplate<?, ?> redisTemplate, @Qualifier("history") ChannelTopic topic) {
            this.redisTemplate = redisTemplate;
            this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Ticket.class));
            this.topic = topic;
        }

        public void publish(Ticket ticket) {
            log.info("Sending " + ticket);
            redisTemplate.convertAndSend(topic.getTopic(), ticket);
        }

}
