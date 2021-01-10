package com.epam.kitchenservice.configuration;

import com.epam.kitchenservice.dto.HistoryEvent;
import com.epam.kitchenservice.event.Event;
import com.epam.kitchenservice.publisher.KitchenHistoryPublisher;
import com.epam.kitchenservice.publisher.KitchenPublisher;
import com.epam.kitchenservice.subscriber.KitchenSubscriber;
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
    private KitchenSubscriber kitchenSubscriber;

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
    public RedisTemplate<String, HistoryEvent> kitchenHistoryRedisTemplate() {
        final RedisTemplate<String, HistoryEvent> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(HistoryEvent.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(kitchenSubscriber);
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("kitchenChannel");
    }

    @Bean
    KitchenPublisher redisPublisher() {
        return new KitchenPublisher(redisTemplate(), publishTopic());
    }
    @Bean
    ChannelTopic publishTopic() {
        return new ChannelTopic("sagaChannel");
    }

    @Bean
    KitchenHistoryPublisher redisHistoryPublisher() {
        return new KitchenHistoryPublisher(kitchenHistoryRedisTemplate(), publishHistoryTopic());
    }
    @Bean("history")
    ChannelTopic publishHistoryTopic() {
        return new ChannelTopic("history");
    }

    @Autowired
    public void setKitchenSubscriber(KitchenSubscriber kitchenSubscriber) {
        this.kitchenSubscriber = kitchenSubscriber;
    }
}
