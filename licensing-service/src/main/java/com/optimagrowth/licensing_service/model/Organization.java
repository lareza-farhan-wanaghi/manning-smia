package com.optimagrowth.licensing_service.model;
import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@RedisHash("organization") 
public class Organization {
    @Id
    String id;
    String name;
    String licenseId;
}