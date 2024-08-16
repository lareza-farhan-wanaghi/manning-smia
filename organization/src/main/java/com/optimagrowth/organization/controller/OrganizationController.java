package com.optimagrowth.organization.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.service.OrganizationService;
import com.optimagrowth.organization.utils.UserContextHolder;

@RestController
@RequestMapping(value="v1")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;
    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value="/{organizationId}") 
    public ResponseEntity<Organization> getLicense(
        @PathVariable("organizationId") String organizationId) { 
            logger.debug("OrganizationController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
            Organization organization = organizationService.getOrganization(organizationId); 
            return ResponseEntity.ok(organization);
    }
}
