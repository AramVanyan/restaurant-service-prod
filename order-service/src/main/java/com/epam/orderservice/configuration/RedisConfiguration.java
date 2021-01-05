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
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    private OrderSubscriber orderSubscriber;
    private SagaEventSubscriber sagaEventSubscriber;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), orderTopic());
        container.addMessageListener(messageListenerSaga(), sagaTopic());
        return container;
    }

    @Bean
    MessageListenerAdapter messageListener() { return new MessageListenerAdapter(orderSubscriber); }
    @Bean
    ChannelTopic orderTopic() {
        return new ChannelTopic("orderChannel");
    }


    @Bean
    MessageListenerAdapter messageListenerSaga() {
        return new MessageListenerAdapter(sagaEventSubscriber);
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
    ChannelTopic orderEventTopic() {
        return new ChannelTopic("orderEventChannel");
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
        return new OrderHistoryPublisher(redisTemplate, historyTopic());
    }

    @Bean
    ChannelTopic historyTopic() {
        return new ChannelTopic("history");
    }

    @Autowired
    public void setOrderSubscriber(OrderSubscriber orderSubscriber) {
        this.orderSubscriber = orderSubscriber;
    }

    @Autowired
    public void setSagaEventSubscriber(SagaEventSubscriber sagaEventSubscriber) {
        this.sagaEventSubscriber = sagaEventSubscriber;
    }
}

