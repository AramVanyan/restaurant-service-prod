package com.epam.orderservice.configuration;

import com.epam.orderservice.dto.DeliveryDto;
import com.epam.orderservice.dto.HistoryEvent;
import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.dto.TicketDto;
import com.epam.orderservice.event.Event;
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
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

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
    public RedisTemplate<String, PaymentDto> paymentRedisTemplate() {
        final RedisTemplate<String, PaymentDto> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(PaymentDto.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, Event> orderEventRedisTemplate() {
        final RedisTemplate<String, Event> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Event.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, TicketDto> kitchenRedisTemplate() {
        final RedisTemplate<String, TicketDto> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(TicketDto.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, DeliveryDto> deliveryRedisTemplate() {
        final RedisTemplate<String, DeliveryDto> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(DeliveryDto.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, HistoryEvent> orderHistoryRedisTemplate() {
        final RedisTemplate<String, HistoryEvent> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(HistoryEvent.class));
        return template;
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
    PaymentPublisher paymentPublisher() {
        return new PaymentPublisher(paymentRedisTemplate(), paymentTopic());
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
    KitchenPublisher kitchenPublisher() {
        return new KitchenPublisher(kitchenRedisTemplate(), kitchenTopic());
    }
    @Bean
    ChannelTopic kitchenTopic() {
        return new ChannelTopic("kitchenChannel");
    }

    @Bean
    DeliveryPublisher deliveryPublisher() {
        return new DeliveryPublisher(deliveryRedisTemplate(), deliveryTopic());
    }
    @Bean
    ChannelTopic deliveryTopic() {
        return new ChannelTopic("deliveryChannel");
    }

    @Bean
    OrderHistoryPublisher redisHistoryPublisher() {
        return new OrderHistoryPublisher(orderHistoryRedisTemplate(), historyTopic());
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

