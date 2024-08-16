package com.optimagrowth.licensing_service.service;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.optimagrowth.licensing_service.config.ServiceConfig;
import com.optimagrowth.licensing_service.model.License;
import com.optimagrowth.licensing_service.model.Organization;
import com.optimagrowth.licensing_service.repository.LicenseRepository;
import com.optimagrowth.licensing_service.service.client.OrganizationRestTemplateClient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.observation.annotation.Observed;

@Service
public class LicenseService {
    @Autowired
    MessageSource messages;
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    ServiceConfig config;
    @Autowired
    OrganizationRestTemplateClient OrganizationRestTemplateClient;

    public License getLicense(String licenseId, String organizationId){
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
           throw new IllegalArgumentException(String.format(messages.getMessage("license.search.error.message", null, null), licenseId, organizationId));
        }
       return license;
    }

    public License createLicense(License license){
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license;
    }

	public License updateLicense(License license){
		licenseRepository.save(license);
		return license;
	}

    @Observed(name = "organization.delete",
			contextualName = "deleting-organization",
			lowCardinalityKeyValues = {"userType", "random"})
    public String deleteLicense(String licenseId){
        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage("license.delete.message", null, null),licenseId);
        return responseMessage;
    }

    // @CircuitBreaker(name = "licenseService", fallbackMethod = "fallbackOrganization")
    // @Bulkhead(name = "licenseService", fallbackMethod = "fallbackOrganization", type = Bulkhead.Type.THREADPOOL)
    // @Retry(name = "retryLicenseService", fallbackMethod = "fallbackOrganization")
    // @RateLimiter(name = "licenseService",fallbackMethod = "fallbackOrganization")
    public Organization getLicenseByOrganization(String organizationId) {
        return OrganizationRestTemplateClient.getOrganization(organizationId);
    }
    
    @SuppressWarnings("unused")
    private Organization fallbackOrganization(String organizationId, Throwable t){
        Organization organization = new Organization();
        organization.setName("fallback");
        organization.setId("fallback");
        return organization;
    }

}