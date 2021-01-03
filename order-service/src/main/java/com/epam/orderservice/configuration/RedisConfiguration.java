package com.epam.orderservice.configuration;

import com.epam.orderservice.publisher.DeliveryPublisher;
import com.epam.orderservice.publisher.KitchenPublisher;
import com.epam.orderservice.publisher.OrderHistoryPublisher;
import com.epam.orderservice.publisher.PaymentPublisher;
import com.epam.orderservice.subscriber.OrderSubscriber;
import com.epam.orderservice.subscriber.SagaEventSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), orderTopic());
        return container;
    }

    @Bean
    MessageListenerAdapter messageListener() { return new MessageListenerAdapter(new OrderSubscriber()); }
    @Bean
    ChannelTopic orderTopic() {
        return new ChannelTopic("createOrder");
    }


    @Bean
    MessageListenerAdapter messageListenerSaga() {
        return new MessageListenerAdapter(new SagaEventSubscriber());
    }
    @Bean
    ChannelTopic sagaTopic() {
        return new ChannelTopic("sagaChannel");
    }

    @Bean
    PaymentPublisher paymentPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new PaymentPublisher(redisTemplate, paymentTopic());
    }
    @Bean
    ChannelTopic paymentTopic() {
        return new ChannelTopic("paymentChannel");
    }

    @Bean
    KitchenPublisher kitchenPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new KitchenPublisher(redisTemplate, kitchenTopic());
    }
    @Bean
    ChannelTopic kitchenTopic() {
        return new ChannelTopic("kitchenChannel");
    }

    @Bean
    DeliveryPublisher deliveryPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new DeliveryPublisher(redisTemplate, deliveryTopic());
    }
    @Bean
    ChannelTopic deliveryTopic() {
        return new ChannelTopic("deliveryChannel");
    }

    @Bean
    OrderHistoryPublisher redisHistoryPublisher(@Autowired RedisTemplate<?, ?> redisTemplate) {
        return new OrderHistoryPublisher(redisTemplate, publishHistoryTopic());
    }

    @Bean("history")
    ChannelTopic publishHistoryTopic() {
        return new ChannelTopic("history");
    }
}

