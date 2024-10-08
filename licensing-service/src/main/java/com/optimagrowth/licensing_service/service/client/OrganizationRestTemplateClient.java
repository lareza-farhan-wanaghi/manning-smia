package com.optimagrowth.licensing_service.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.optimagrowth.licensing_service.model.Organization;
import com.optimagrowth.licensing_service.repository.OrganizationRedisRepository;
import com.optimagrowth.licensing_service.service.utils.UserContext;
import com.optimagrowth.licensing_service.service.utils.UserContextHolder;



@Component
public class OrganizationRestTemplateClient {
   @Autowired
   RestTemplate restTemplate;
   @Autowired
   OrganizationRedisRepository redisRepository;
   private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

   private Organization checkRedisCache(String organizationId) {
      try {
         return redisRepository.findById(organizationId).orElse(null);  
      } catch (Exception ex){
         logger.error("Error encountered while trying to retrieve organization{} check Redis Cache.  Exception {}", organizationId, ex);
         return null;
      }
   }
   private void cacheOrganizationObject(Organization organization) {
      try {
         redisRepository.save(organization); 
         logger.error("Cache organization {} in Redis.", organization.toString());
      } catch (Exception ex) {
         logger.error("Unable to cache organization {} in Redis. Exception {}", organization.getId(), ex);
      } 
   }
   public Organization getOrganization(String organizationId){
      logger.debug("In Licensing Service.getOrganization: {}", UserContextHolder.getContext().getCorrelationId());
      Organization organization = checkRedisCache(organizationId);
      if (organization != null) { 
         logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, organization);
         return organization;
      }
      logger.debug("Unable to locate organization from the redis cache: {}.",organizationId);
      
      ResponseEntity<Organization> restExchange = restTemplate.exchange("http://gateway-server:8072/organization-service/v1/"+organizationId,HttpMethod.GET,
        null, Organization.class, organizationId);
      organization = restExchange.getBody();
      if (organization != null) {
         cacheOrganizationObject(organization);
      }
      return restExchange.getBody();
   }
}

