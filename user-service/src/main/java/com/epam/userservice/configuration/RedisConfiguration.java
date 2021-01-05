package com.epam.userservice.configuration;

import com.epam.userservice.dto.OrderDto;
import com.epam.userservice.publisher.OrderPublisher;
import com.epam.userservice.subscriber.OrderEventSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    private OrderEventSubscriber orderEventSubscriber;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, OrderDto> redisTemplate() {
        final RedisTemplate<String, OrderDto> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(OrderDto.class));
        return template;
    }

    @Bean
    OrderPublisher redisPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new OrderPublisher(redisTemplate, topic());
    }
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("orderChannel");
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(orderEventSubscriber);
    }

    @Autowired
    public void setOrderEventSubscriber(OrderEventSubscriber orderEventSubscriber) {
        this.orderEventSubscriber = orderEventSubscriber;
    }
}
