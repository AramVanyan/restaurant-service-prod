package com.epam.userservice.configuration;

import com.epam.userservice.publisher.OrderPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class RedisConfiguration {

    @Bean
    OrderPublisher redisPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new OrderPublisher(redisTemplate, topic());
    }
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("createOrder");
    }
}
