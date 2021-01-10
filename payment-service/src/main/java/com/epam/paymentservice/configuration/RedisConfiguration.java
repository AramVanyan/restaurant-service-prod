package com.epam.paymentservice.configuration;

import com.epam.paymentservice.dto.HistoryEvent;
import com.epam.paymentservice.event.Event;
import com.epam.paymentservice.publisher.EventPublisher;
import com.epam.paymentservice.publisher.PaymentHistoryPublisher;
import com.epam.paymentservice.subscriber.PaymentSubscriber;
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
    private PaymentSubscriber paymentSubscriber;
    private EventPublisher eventPublisher;

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
    public RedisTemplate<String, HistoryEvent> paymentHistoryRedisTemplate() {
        final RedisTemplate<String, HistoryEvent> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(HistoryEvent.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(paymentSubscriber);
    }
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("paymentChannel");
    }

    @Bean
    EventPublisher redisPublisher() { return eventPublisher; }
    @Bean
    ChannelTopic sagaChannel() {
        return new ChannelTopic("sagaChannel");
    }

    @Bean
    PaymentHistoryPublisher redisHistoryPublisher() {
        return new PaymentHistoryPublisher(paymentHistoryRedisTemplate(), publishHistoryTopic());
    }
    @Bean
    ChannelTopic publishHistoryTopic() {
        return new ChannelTopic("history");
    }

    @Autowired
    public void setPaymentSubscriber(PaymentSubscriber paymentSubscriber) {
        this.paymentSubscriber = paymentSubscriber;
    }

    @Autowired
    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
