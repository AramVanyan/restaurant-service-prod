package com.epam.kitchenservice.configuration;

import com.epam.kitchenservice.publisher.KitchenHistoryPublisher;
import com.epam.kitchenservice.publisher.KitchenPublisher;
import com.epam.kitchenservice.subscriber.KitchenSubscriber;
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
        return new MessageListenerAdapter(new KitchenSubscriber());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("kitchenChannel");
    }
    @Bean
    KitchenPublisher redisPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new KitchenPublisher(redisTemplate, publishTopic());
    }
    @Bean
    ChannelTopic publishTopic() {
        return new ChannelTopic("sagaChannel");
    }

    @Bean
    KitchenHistoryPublisher redisHistoryPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new KitchenHistoryPublisher(redisTemplate, publishHistoryTopic());
    }
    @Bean("history")
    ChannelTopic publishHistoryTopic() {
        return new ChannelTopic("history");
    }
}
