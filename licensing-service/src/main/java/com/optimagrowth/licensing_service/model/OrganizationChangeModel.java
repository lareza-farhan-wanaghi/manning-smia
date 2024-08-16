package com.optimagrowth.licensing_service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @ToString
public class OrganizationChangeModel {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;
    // No-argument constructor is needed for Jackson deserialization
    public OrganizationChangeModel() {
    }
    public OrganizationChangeModel(String type,
            String action, String organizationId,
            String correlationId) {
        this.type = type;
        this.action = action;
        this.organizationId = organizationId;
        this.correlationId = correlationId;
    } 
}