package com.optimagrowth.licensing_service.contoller;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.optimagrowth.licensing_service.model.License;
import com.optimagrowth.licensing_service.model.Organization;
import com.optimagrowth.licensing_service.service.LicenseService;
import com.optimagrowth.licensing_service.service.utils.UserContextHolder; 

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;
    private static final Logger logger = LoggerFactory.getLogger(LicenseController.class);

    @GetMapping(value="/{licenseId}") 
    public ResponseEntity<License> getLicense(
        @PathVariable("organizationId") String organizationId, 
        @PathVariable("licenseId") String licenseId) { 
            License license = licenseService.getLicense(licenseId,organizationId); 
            return ResponseEntity.ok(license);
    }

    @PostMapping 
    public ResponseEntity<License> createLicense(
        @RequestBody License request,
        @RequestHeader(value = "Accept-Language",required = false) Locale locale) {
            return ResponseEntity.ok(licenseService.createLicense(request));
    }

	@PutMapping
	public ResponseEntity<License> updateLicense(@RequestBody License request) {
		return ResponseEntity.ok(licenseService.updateLicense(request));
	}


    @DeleteMapping(value="/{licenseId}") 
    public ResponseEntity<String> deleteLicense(
        @PathVariable("organizationId") String organizationId,
        @PathVariable("licenseId") String licenseId) {
            return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }
    
    @GetMapping
    public ResponseEntity<Organization> getLicenseByOrganization(
        @PathVariable("organizationId") String organizationId) {
        logger.debug("LicenseController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return ResponseEntity.ok(licenseService.getLicenseByOrganization(organizationId));
    }

}