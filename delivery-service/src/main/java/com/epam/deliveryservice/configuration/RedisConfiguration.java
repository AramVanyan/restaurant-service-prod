package com.epam.deliveryservice.configuration;

import com.epam.deliveryservice.event.Event;
import com.epam.deliveryservice.publisher.DeliveryHistoryPublisher;
import com.epam.deliveryservice.publisher.DeliveryPublisher;
import com.epam.deliveryservice.subscriber.DeliverySubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    private DeliverySubscriber deliverySubscriber;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(messageListener(), topic());
        container.setConnectionFactory(jedisConnectionFactory());
        return container;
    }

    @Bean
    public RedisTemplate<String, Event> redisTemplate() {
        final RedisTemplate<String, Event> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Event.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(deliverySubscriber);
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

    public DeliverySubscriber getDeliverySubscriber() {
        return deliverySubscriber;
    }

    @Autowired
    public void setDeliverySubscriber(DeliverySubscriber deliverySubscriber) {
        this.deliverySubscriber = deliverySubscriber;
    }
}
