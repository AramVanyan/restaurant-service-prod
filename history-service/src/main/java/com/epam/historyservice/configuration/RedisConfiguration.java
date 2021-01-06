package com.epam.historyservice.configuration;

import com.epam.historyservice.subscriber.HistorySubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    private HistorySubscriber historySubscriber;

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
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(historySubscriber);
    }

    @Bean()
    ChannelTopic topic() {
        return new ChannelTopic("history");
    }

    @Autowired
    public void setHistorySubscriber(HistorySubscriber historySubscriber) {
        this.historySubscriber = historySubscriber;
    }
}
