package com.optimagrowth.licensing_service.events;

import java.io.IOException;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimagrowth.licensing_service.LicensingServiceApplication;
import com.optimagrowth.licensing_service.model.OrganizationChangeModel;

@Configuration
public class StreamConfig {
    private static final Logger logger = LoggerFactory.getLogger(LicensingServiceApplication.class);
    
    @Bean
    public Consumer<String> loggerSink() {
        return payload -> {
            try {
                logger.debug("Received an event for organization id {}", payload);
                OrganizationChangeModel orgChange = new ObjectMapper().readValue(payload, OrganizationChangeModel.class);
                logger.debug("Received an {} event for organization id {}", 
                    orgChange.getAction(), orgChange.getOrganizationId());
            } catch (IOException e) {
                logger.error("Failed to deserialize payload", e);
            }
        };
    }
}
