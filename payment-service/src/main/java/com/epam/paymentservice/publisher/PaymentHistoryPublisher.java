package com.epam.paymentservice.publisher;

import com.epam.paymentservice.dto.HistoryEvent;
import com.epam.paymentservice.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentHistoryPublisher {
        private final RedisTemplate<?, ?> redisTemplate;
        private final ChannelTopic topic;


        @Autowired
        public PaymentHistoryPublisher(RedisTemplate<?, ?> redisTemplate, @Qualifier("publishHistoryTopic") ChannelTopic topic) {
            this.redisTemplate = redisTemplate;
            this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Payment.class));
            this.topic = topic;
        }

        public void publish(HistoryEvent historyEvent) {
            log.info("Sending " + historyEvent);
            redisTemplate.convertAndSend(topic.getTopic(), historyEvent);
        }

}
