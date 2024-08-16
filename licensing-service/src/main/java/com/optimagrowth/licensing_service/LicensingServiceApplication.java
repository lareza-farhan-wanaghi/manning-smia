package com.optimagrowth.licensing_service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.optimagrowth.licensing_service.config.ServiceConfig;
import com.optimagrowth.licensing_service.service.utils.UserContextInterceptor;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;

@SpringBootApplication
@RefreshScope
public class LicensingServiceApplication {
	
   	@Autowired
   	private ServiceConfig serviceConfig;

	public static void main(String[] args) {
		SpringApplication.run(LicensingServiceApplication.class, args);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);  
		return localeResolver;
	}

	@Bean
   	public ResourceBundleMessageSource messageSource() {
     	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setUseCodeAsDefaultMessage(true); 
		messageSource.setBasenames("messages"); 
		return messageSource;
   }

   	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(){
		RestTemplate template = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
		if (interceptors == null){ 
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		}else{
			interceptors.add(new UserContextInterceptor());
			template.setInterceptors(interceptors);
		}
		return template;
	}

	@Bean 
	JedisConnectionFactory jedisConnectionFactory() {
       String hostname = serviceConfig.getRedisServer();
       int port = Integer.parseInt(serviceConfig.getRedisPort());
       RedisStandaloneConfiguration redisStandaloneConfiguration
           = new RedisStandaloneConfiguration(hostname, port);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}
	@Bean 
	public RedisTemplate<String, Object> redisTemplate() {
       RedisTemplate<String, Object> template = new RedisTemplate<>();
       template.setConnectionFactory(jedisConnectionFactory());
       return template;
	}

}
