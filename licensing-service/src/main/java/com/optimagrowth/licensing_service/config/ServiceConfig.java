package com.optimagrowth.licensing_service.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component 
@Getter
public class ServiceConfig{
    @Value("${redis.server}")
    private String redisServer="";
    @Value("${redis.port}")
    private String redisPort="";
}