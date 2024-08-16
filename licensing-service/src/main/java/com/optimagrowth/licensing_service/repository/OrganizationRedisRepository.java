package com.optimagrowth.licensing_service.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.optimagrowth.licensing_service.model.Organization;

@Repository
public interface OrganizationRedisRepository extends CrudRepository<Organization,String>{}