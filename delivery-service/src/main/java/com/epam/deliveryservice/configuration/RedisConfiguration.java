package com.epam.deliveryservice.configuration;

import com.epam.deliveryservice.publisher.DeliveryHistoryPublisher;
import com.epam.deliveryservice.publisher.DeliveryPublisher;
import com.epam.deliveryservice.subscriber.DeliverySubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(messageListener(), topic());
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new DeliverySubscriber());
    }

    @Bean()
    ChannelTopic topic() {
        return new ChannelTopic("deliveryChannel");
    }

    @Bean
    DeliveryPublisher redisPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new DeliveryPublisher(redisTemplate, publishTopic());
    }
    @Bean
    ChannelTopic publishTopic() {
        return new ChannelTopic("sagaChannel");
    }

    @Bean
    DeliveryHistoryPublisher redisHistoryPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new DeliveryHistoryPublisher(redisTemplate, publishHistoryTopic());
    }
    @Bean
    ChannelTopic publishHistoryTopic() {
        return new ChannelTopic("history");
    }

}
