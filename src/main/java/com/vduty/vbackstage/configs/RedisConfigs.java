package com.vduty.vbackstage.configs;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vduty.vbackstage.entity.UsersBaseEntity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfigs {
		
	   /* @Bean
	    JedisConnectionFactory connectionFactory() {
	        return new JedisConnectionFactory();
	    }*/

	    @Bean
	    ValueOperations<String, String> strOperations(RedisTemplate<String, String> redisTemplate) {
	        return redisTemplate.opsForValue();
	    }

	    @Bean
	    RedisTemplate<String, Integer> intRedisTemplate(JedisConnectionFactory connectionFactory) {
	        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<String, Integer>();
	        redisTemplate.setConnectionFactory(connectionFactory);
	        return redisTemplate;
	    }
	    
	    /**
	     * 存储users对象的redis bean
	     * @param connectionFactory
	     * @return
	     */
	    @Bean 
	    RedisTemplate<String, UsersBaseEntity> userRedisTemplate(JedisConnectionFactory connectionFactory) {
	        RedisTemplate<String, UsersBaseEntity> redisTemplate = new RedisTemplate<String, UsersBaseEntity>();
	        redisTemplate.setConnectionFactory(connectionFactory);
	        return redisTemplate;
	    }

	    @Bean
	    ValueOperations<String, Integer> intOperations(RedisTemplate<String, Integer> redisTemplate) {
	        return redisTemplate.opsForValue();
	    }

	   @Bean
	    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper objectMapper) {
	        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =  new Jackson2JsonRedisSerializer<Object>(Object.class);   // new Jackson2JsonRedisSerializer<Object>(Object.class);
	        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
	        return jackson2JsonRedisSerializer;
	    }

	   
	    

	

	

}
