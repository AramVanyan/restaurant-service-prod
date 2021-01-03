package com.epam.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@SpringBootApplication
public class UserServiceApplication {
	private RedisServer redisServer;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@PostConstruct
	public void startRedis() throws IOException {
		redisServer  = new RedisServer(6379);
		redisServer.start();
	}

	@PreDestroy
	public void stopRedis(){
		redisServer.stop();
	}
}
