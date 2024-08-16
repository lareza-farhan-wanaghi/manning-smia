package com.optimagrowth.organization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimagrowth.organization.events.source.ActionEnum;
import com.optimagrowth.organization.events.source.SimpleSourceBean;
import com.optimagrowth.organization.model.Organization;

@Service
public class OrganizationService {
    @Autowired
    SimpleSourceBean simpleSourceBean; 

    public Organization getOrganization(String organizationId){
        Organization organization = new Organization();
        organization.setId(organizationId);
        organization.setName("Organization with Id "+ organizationId);
        organization.setLicenseId("lcns"+ organizationId);
        simpleSourceBean.publishOrganizationChange(ActionEnum.CREATED,organizationId);
       return organization;
   }
}
