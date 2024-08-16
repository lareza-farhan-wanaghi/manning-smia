package com.optimagrowth.organization.events.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimagrowth.organization.model.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import com.optimagrowth.organization.utils.UserContextHolder;

@Component
public class SimpleSourceBean {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);
    private final StreamBridge streamBridge;

    @Autowired
    public SimpleSourceBean(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrganizationChange(ActionEnum action, String organizationId) {
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);

        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action.toString(),
                organizationId,
                UserContextHolder.getContext().getCorrelationId());

        String jsonPayload ="";
        try {
            jsonPayload = new ObjectMapper().writeValueAsString(change);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } 
        Message<String> message = MessageBuilder.withPayload(jsonPayload).build();
        streamBridge.send("organizationChange-out-0", message);
    }
}
